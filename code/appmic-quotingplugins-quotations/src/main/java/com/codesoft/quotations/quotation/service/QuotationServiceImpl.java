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
      log.info("Creating quotation with data: {}", requestDto);
      final QuotationEntity entityMapped = this.quotationFieldsMapper.toEntity(requestDto);
      log.info("Orquestación: Obtener datos de otros micro servicios para completar la creación de la cotización.");
      final CustomerResponseDto customerResponseDto = fetchCustomer(requestDto.getCustomerDocumentNumber());
      final EmployeeResponseDto employeeResponseDto = fetchEmployee(requestDto.getEmployeeDocumentNumber());
      entityMapped.setState("BORRADOR");
      entityMapped.setCustomerDocumentNumber(customerResponseDto.getDocumentNumber());
      entityMapped.setEmployeeDocumentNumber(employeeResponseDto.getDocumentNumber());

      BigDecimal totalQuotationPrice = BigDecimal.ZERO;

      log.info("Iniciando llamada al micro servicio de materiales para validar datos relacionados.");
      for (var detail : entityMapped.getDetails()) {
        log.info("Consultando conceptos del modulo con ID: {}", detail.getModule().getId());
        ModuleEntity moduleEntity = this.moduleRepository.findById(detail.getModule().getId())
          .orElseThrow(() -> new QuotationException(QuotationMessage.MODULE_NOT_FOUND));
        log.info("Módulo encontrado: {}", moduleEntity);
        detail.setModule(moduleEntity);

        // 3. PROCESAR MATERIALES (Primero sumamos todos).
        BigDecimal subItemsTotalPrice = BigDecimal.ZERO;
        for (var subItem : detail.getSubItems()) {
          final MaterialResponseDto materialResponse = fetchMaterial(subItem.getMaterialId());
          log.info("Material encontrado: {}", materialResponse);
          subItem.setMaterialId(materialResponse.getId());
          subItem.calculateUnitPrice(); // rawMaterialCost / pieces
          subItem.calculateTotalPrice(); // unitPrice * quantity
          subItemsTotalPrice = subItemsTotalPrice.add(subItem.getTotalPrice());
        }

        // 4. PROCESAR MATERIALES (Primero sumamos todos).
        BigDecimal baseCost = subItemsTotalPrice
          .add(Optional.ofNullable(detail.getTransportationCost()).orElse(BigDecimal.ZERO))
          .add(Optional.ofNullable(detail.getLaborCost()).orElse(BigDecimal.ZERO))
          .add(Optional.ofNullable(detail.getPackingCost()).orElse(BigDecimal.ZERO));

        // 5. CASCADA FINANCIERA (Multiplicación Acumulativa).
        // Guardamos los porcentajes para el snapshot.
        detail.setOverheadsPercentage(moduleEntity.getOverheadsCostPercentage());
        detail.setFeePercentage(moduleEntity.getFeePercentage());
        detail.setRebatePercentage(moduleEntity.getRebatePercentage());
        detail.setProfitMarginPercentage(moduleEntity.getProfitMarginPercentage());

        // GASTOS GENERALES: Base * (1 + GG%)
        BigDecimal withOverheads = calculateCascadingStep(baseCost, detail.getOverheadsPercentage());
        detail.setOverheadsAmount(withOverheads.subtract(baseCost));

        // FEE: (Base + GG) * (1 + FEE%)
        BigDecimal withFee = calculateCascadingStep(withOverheads, detail.getFeePercentage());
        detail.setFeeAmount(withFee.subtract(withOverheads));

        // REBATE: (Base + GG + FEE) * (1 + Rebate%)
        BigDecimal withRebate = calculateCascadingStep(withFee, detail.getRebatePercentage());
        detail.setRebateAmount(withRebate.subtract(withFee));

        // UTILIDAD: (Base + GG + FEE + Rebate) * (1 + Margen%)
        BigDecimal finalPriceUnit = calculateCascadingStep(withRebate, detail.getProfitMarginPercentage());

        // 6. ASIGNACIÓN FINAL DE LÍNEA
        detail.setUnitProductionCost(finalPriceUnit.setScale(2, RoundingMode.HALF_UP));
        detail.setSuggestedPrice(detail.getUnitProductionCost());
        detail.setTotalLinePrice(detail.getSuggestedPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));

        totalQuotationPrice = totalQuotationPrice.add(detail.getTotalLinePrice());

      }
      entityMapped.setTotalProductionCost(totalQuotationPrice);

      log.info("Mapped final quotation entity: {}", entityMapped);
      final QuotationEntity saved = this.quotationRepository.save(entityMapped);

      QuotationResponseDto response = this.quotationFieldsMapper.toDto(saved);
      response.setCustomerName(customerResponseDto.getCompanyName());
      response.setEmployeeName(employeeResponseDto.getFullName());

      return response;
    } catch (final Exception ex) {
      log.error("Error inesperado en la orquestación: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.QUOTATION_INTERNAL_ERROR);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.quotationRepository.findById(id)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
    this.quotationRepository.deleteById(id);
  }

  /**
   * Aplica la fórmula: Base * (1 + (Porcentaje / 100))
   */
  private BigDecimal calculateCascadingStep(final BigDecimal base, final BigDecimal percentage) {
    if (percentage == null || percentage.compareTo(BigDecimal.ZERO) == 0) {
      return base;
    }
    // factor = 1 + (3 / 100) = 1.03
    BigDecimal factor = BigDecimal.ONE.add(percentage.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
    return base.multiply(factor);
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
