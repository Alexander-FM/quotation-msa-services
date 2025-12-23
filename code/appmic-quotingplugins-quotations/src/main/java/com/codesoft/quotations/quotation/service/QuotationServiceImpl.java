package com.codesoft.quotations.quotation.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import com.codesoft.quotations.client.customer.dto.CustomerResponseDto;
import com.codesoft.quotations.client.customer.service.CustomerClient;
import com.codesoft.quotations.client.employee.dto.EmployeeResponseDto;
import com.codesoft.quotations.client.employee.service.EmployeeClient;
import com.codesoft.quotations.client.material.dto.MaterialResponseDto;
import com.codesoft.quotations.client.material.service.MaterialClient;
import com.codesoft.quotations.modules.module.entity.ModuleEntity;
import com.codesoft.quotations.modules.module.repository.ModuleRepository;
import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationEntity;
import com.codesoft.quotations.quotation.exception.QuotationException;
import com.codesoft.quotations.quotation.exception.QuotationMessage;
import com.codesoft.quotations.quotation.mapper.QuotationDetailFieldsMapper;
import com.codesoft.quotations.quotation.mapper.QuotationDetailSubItemFieldsMapper;
import com.codesoft.quotations.quotation.mapper.QuotationFieldsMapper;
import com.codesoft.quotations.quotation.repository.QuotationRepository;
import com.codesoft.utils.GenericResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

  private final QuotationRepository quotationRepository;

  private final ModuleRepository moduleRepository;

  private final QuotationFieldsMapper quotationFieldsMapper;

  private final QuotationDetailFieldsMapper quotationDetailFieldsMapper;

  private final QuotationDetailSubItemFieldsMapper quotationDetailSubItemFieldsMapper;

  private final MaterialClient materialClient;

  private final CustomerClient customerClient;

  private final EmployeeClient employeeClient;

  @Override
  public List<QuotationResponseDto> findAll() {
    final List<QuotationEntity> entities = this.quotationRepository.findAll();
    return quotationFieldsMapper.toDtoList(entities);
  }

  @Override
  public QuotationResponseDto findById(Integer id) {
    final Optional<QuotationEntity> entityOptional = this.quotationRepository.findById(id);
    return entityOptional.map(this.quotationFieldsMapper::toDto)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
  }

  @Override
  @Transactional
  public QuotationResponseDto create(final QuotationRequestDto requestDto) {
    try {
      final QuotationEntity entityMapped = this.quotationFieldsMapper.toEntity(requestDto);

      // Orquestación de nombres
      final CustomerResponseDto customer = fetchCustomer(requestDto.getCustomerDocumentNumber());
      final EmployeeResponseDto employee = fetchEmployee(requestDto.getEmployeeDocumentNumber());

      entityMapped.setState("BORRADOR");
      entityMapped.setCustomerDocumentNumber(customer.getDocumentNumber());
      entityMapped.setEmployeeDocumentNumber(employee.getDocumentNumber());

      BigDecimal totalQuotationPrice = BigDecimal.ZERO;

      for (var detail : entityMapped.getDetails()) {
        ModuleEntity module = this.moduleRepository.findById(detail.getModule().getId())
          .orElseThrow(() -> new QuotationException(QuotationMessage.MODULE_NOT_FOUND));

        detail.setModule(module);

        // 1. SUMA DE MATERIALES
        BigDecimal materialsBase = BigDecimal.ZERO;
        for (var subItem : detail.getSubItems()) {
          final MaterialResponseDto matRes = fetchMaterial(subItem.getMaterialId());
          subItem.setMaterialId(matRes.getId());
          subItem.calculateUnitPrice();
          subItem.calculateTotalPrice();
          materialsBase = materialsBase.add(subItem.getTotalPrice());
        }

        // 2. COSTO BASE (Materiales + Fijos)
        BigDecimal currentSubtotal = materialsBase
          .add(Optional.ofNullable(detail.getTransportationCost()).orElse(BigDecimal.ZERO))
          .add(Optional.ofNullable(detail.getLaborCost()).orElse(BigDecimal.ZERO))
          .add(Optional.ofNullable(detail.getPackingCost()).orElse(BigDecimal.ZERO));

        // SNAPSHOT DE PORCENTAJES
        detail.setOverheadsPercentage(module.getOverheadsCostPercentage());
        detail.setFeePercentage(module.getFeePercentage());
        detail.setRebatePercentage(module.getRebatePercentage());
        detail.setProfitMarginPercentage(module.getProfitMarginPercentage());

        // 3. CASCADA FINANCIERA (Redondeo a 2 decimales en cada paso)
        BigDecimal cien = new BigDecimal("100");

        // GASTOS GENERALES (GG)
        BigDecimal gg = currentSubtotal.multiply(detail.getOverheadsPercentage().divide(cien, 4, RoundingMode.HALF_UP))
          .setScale(2, RoundingMode.HALF_UP);
        detail.setOverheadsAmount(gg);
        currentSubtotal = currentSubtotal.add(gg);

        // FEE: Sobre (Base + GG)
        BigDecimal fee = currentSubtotal.multiply(detail.getFeePercentage().divide(cien, 4, RoundingMode.HALF_UP))
          .setScale(2, RoundingMode.HALF_UP);
        detail.setFeeAmount(fee);
        currentSubtotal = currentSubtotal.add(fee);

        // REBATE: Sobre (Base + GG + FEE)
        BigDecimal rebate = currentSubtotal.multiply(detail.getRebatePercentage().divide(cien, 4, RoundingMode.HALF_UP))
          .setScale(2, RoundingMode.HALF_UP);
        detail.setRebateAmount(rebate);
        currentSubtotal = currentSubtotal.add(rebate);

        // 4. PRECIO SUGERIDO (Subtotal Acumulado * (1 + Margen%))
        BigDecimal marginFactor = BigDecimal.ONE.add(detail.getProfitMarginPercentage().divide(cien, 4, RoundingMode.HALF_UP));
        BigDecimal unitSugerido = currentSubtotal.multiply(marginFactor).setScale(2, RoundingMode.HALF_UP);

        detail.setUnitProductionCost(unitSugerido);
        detail.setSuggestedPrice(unitSugerido);
        detail.setTotalLinePrice(unitSugerido.multiply(BigDecimal.valueOf(detail.getQuantity())).setScale(2, RoundingMode.HALF_UP));

        totalQuotationPrice = totalQuotationPrice.add(detail.getTotalLinePrice());
      }

      entityMapped.setTotalProductionCost(totalQuotationPrice.setScale(2, RoundingMode.HALF_UP));

      // GUARDAR
      final QuotationEntity saved = this.quotationRepository.save(entityMapped);

      // 5. HIDRATACIÓN DE RESPUESTA (Rich Response)
      QuotationResponseDto response = this.quotationFieldsMapper.toDto(saved);
      response.setCustomerName(customer.getCompanyName());
      response.setEmployeeName(employee.getFullName());

      // Aquí usamos el materialId que ya está en el DTO de respuesta
      response.getDetails().forEach(d -> d.getSubItems().forEach(si -> {
        MaterialResponseDto m = fetchMaterial(si.getMaterialId());
        si.setMaterial(m);
      }));
      return response;
    } catch (Exception ex) {
      log.error("Error crítico: {}", ex.getMessage());
      throw ex;
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.quotationRepository.findById(id)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
    this.quotationRepository.deleteById(id);
  }

  private MaterialResponseDto fetchMaterial(final Integer id) {
    try {
      ResponseEntity<GenericResponse<MaterialResponseDto>> response = this.materialClient.searchMaterialById(id);
      // Caso: El Circuit Breaker se activó y el fallback devolvió una respuesta de error
      if (response.getBody() == null || response.getStatusCode().isError()) {
        log.warn("El servicio de materiales devolvió un error o el fallback se activó para el ID: {}", id);
        throw new QuotationException(QuotationMessage.MATERIAL_SERVICE_UNAVAILABLE);
      }
      return response.getBody().getBody();
    } catch (FeignException.NotFound ex) {
      // Caso: El micro servicio respondió 404 (El material NO existe)
      log.warn("Material con ID {} no encontrado en el catálogo.", id);
      throw new QuotationException(QuotationMessage.MATERIAL_NOT_FOUND);
    } catch (FeignException ex) {
      // Caso: Errores de conexión, timeout o 500 (Infraestructura)
      log.error("Error técnico al llamar al micro servicio de materiales: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.MATERIAL_SERVICE_UNAVAILABLE);
    }
  }

  private CustomerResponseDto fetchCustomer(final String doc) {
    try {
      ResponseEntity<GenericResponse<CustomerResponseDto>> customer = this.customerClient.retrieveByDocumentNumber(doc);
      if (customer.getBody() == null || customer.getStatusCode().isError()) {
        log.warn("El servicio de clientes devolvió un error o el fallback se activó para el DOC: {}", doc);
        throw new QuotationException(QuotationMessage.CUSTOMER_SERVICE_UNAVAILABLE);
      }
      return customer.getBody().getBody();
    } catch (FeignException.NotFound ex) {
      log.warn("Customer con DOC {} no encontrado en el servicio de clientes.", doc);
      throw new QuotationException(QuotationMessage.CUSTOMER_NOT_FOUND);
    } catch (FeignException ex) {
      log.error("Error técnico al llamar al micro servicio de clientes: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.CUSTOMER_SERVICE_UNAVAILABLE);
    }
  }

  private EmployeeResponseDto fetchEmployee(final String doc) {
    try {
      ResponseEntity<GenericResponse<EmployeeResponseDto>> employee = this.employeeClient.retrieveByDocumentNumber(doc);
      if (employee.getBody() == null || employee.getStatusCode().isError()) {
        log.warn("El servicio de empleados devolvió un error o el fallback se activó para el DOC: {}", doc);
        throw new QuotationException(QuotationMessage.EMPLOYEE_SERVICE_UNAVAILABLE);
      }
      return employee.getBody().getBody();
    } catch (FeignException.NotFound ex) {
      log.warn("Employee con DOC {} no encontrado en el servicio de empleados.", doc);
      throw new QuotationException(QuotationMessage.EMPLOYEE_NOT_FOUND);
    } catch (FeignException ex) {
      log.error("Error técnico al llamar al micro servicio de empleados: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.EMPLOYEE_SERVICE_UNAVAILABLE);
    }
  }
}
