package com.codesoft.quotations.quotation.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.codesoft.exception.BaseException;
import com.codesoft.quotations.client.customer.dto.CustomerResponseDto;
import com.codesoft.quotations.client.customer.service.CustomerClient;
import com.codesoft.quotations.client.employee.dto.EmployeeResponseDto;
import com.codesoft.quotations.client.employee.service.EmployeeClient;
import com.codesoft.quotations.client.material.dto.MaterialResponseDto;
import com.codesoft.quotations.client.material.service.MaterialClient;
import com.codesoft.quotations.modules.module.entity.ModuleEntity;
import com.codesoft.quotations.modules.module.repository.ModuleRepository;
import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationDetailSubItemResponseDto;
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
    final List<QuotationResponseDto> dtoList = this.quotationFieldsMapper.toDtoList(entities);

    Set<String> customerIds = dtoList.stream().map(QuotationResponseDto::getCustomerDocumentNumber).collect(Collectors.toSet());
    Set<String> employeeIds = dtoList.stream().map(QuotationResponseDto::getEmployeeDocumentNumber).collect(Collectors.toSet());
    Set<Integer> materialIds = dtoList.stream()
      .flatMap(q -> q.getDetails().stream())
      .flatMap(d -> d.getSubItems().stream())
      .map(QuotationDetailSubItemResponseDto::getMaterialId)
      .collect(Collectors.toSet());

    Map<String, String> customerMap = fetchAllCustomerByDocumentNumberIn(customerIds).stream()
      .collect(Collectors.toMap(CustomerResponseDto::getDocumentNumber, CustomerResponseDto::getCompanyName));
    Map<String, String> employeeMap = fetchEmployeeByDocumentNumberIn(employeeIds).stream()
      .collect(Collectors.toMap(EmployeeResponseDto::getDocumentNumber, EmployeeResponseDto::getFullName));
    Map<Integer, MaterialResponseDto> materialMap = fetchAllMaterialsByIds(materialIds).stream()
      .collect(Collectors.toMap(MaterialResponseDto::getId, m -> m));

    for (QuotationResponseDto dto : dtoList) {
      dto.setCustomerName(customerMap.getOrDefault(dto.getCustomerDocumentNumber(), "Desconocido"));
      dto.setEmployeeName(employeeMap.getOrDefault(dto.getEmployeeDocumentNumber(), "Desconocido"));

      dto.getDetails().forEach(d -> d.getSubItems().forEach(si -> {
        if (materialMap.containsKey(si.getMaterialId())) {
          si.setMaterial(materialMap.get(si.getMaterialId()));
        }
      }));
    }

    return dtoList;
  }

  @Override
  public QuotationResponseDto findById(final Integer id) {
    final QuotationEntity entityOptional =
      this.quotationRepository.findById(id).orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
    final QuotationResponseDto responseDto = this.quotationFieldsMapper.toDto(entityOptional);
    responseDto.setCustomerName(this.fetchCustomer(entityOptional.getCustomerDocumentNumber()).getCompanyName());
    responseDto.setEmployeeName(this.fetchEmployee(entityOptional.getEmployeeDocumentNumber()).getFullName());
    responseDto.getDetails().forEach(d -> d.getSubItems().forEach(si -> {
      MaterialResponseDto m = fetchMaterial(si.getMaterialId());
      si.setMaterial(m);
    }));
    return responseDto;
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

        // factorGG = 1 + (3 / 100) = 1.03
        BigDecimal factorGG = BigDecimal.ONE.add(detail.getOverheadsPercentage().divide(cien, 4, RoundingMode.HALF_UP));
        // withGG = 164.88 * 1.03 = 169.83
        BigDecimal withGG = currentSubtotal.multiply(factorGG).setScale(2, RoundingMode.HALF_UP);
        // Guardamos el subtotal acumulado para que coincida con el Excel
        detail.setOverheadsAmount(withGG);
        currentSubtotal = withGG;

        // FEE: SubtotalGG * (1 + FEE%)
        BigDecimal factorFee = BigDecimal.ONE.add(detail.getFeePercentage().divide(cien, 4, RoundingMode.HALF_UP));
        BigDecimal withFee = currentSubtotal.multiply(factorFee).setScale(2, RoundingMode.HALF_UP);
        detail.setFeeAmount(withFee);
        currentSubtotal = withFee;

        // REBATE: SubtotalFee * (1 + Rebate%)
        BigDecimal factorRebate = BigDecimal.ONE.add(detail.getRebatePercentage().divide(cien, 4, RoundingMode.HALF_UP));
        BigDecimal withRebate = currentSubtotal.multiply(factorRebate).setScale(2, RoundingMode.HALF_UP);
        detail.setRebateAmount(withRebate);
        currentSubtotal = withRebate;

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
    } catch (final QuotationException ex) {
      log.error("Oh dear! There was an error in the quote. Don't worry, we're working to resolve the issue.: {}", ex.getMessage());
      throw ex;
    } catch (final BaseException ex) {
      log.error("A base exception occurred while processing the quotation: {}", ex.getMessage());
      throw ex;
    } catch (final Exception ex) {
      log.error("An unexpected error occurred while processing the quotation: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.QUOTATION_INTERNAL_ERROR);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.quotationRepository.findById(id)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
    this.quotationRepository.deleteById(id);
  }

  private <T> T callFeign(final java.util.function.Supplier<ResponseEntity<GenericResponse<T>>> supplier,
    final QuotationMessage notFoundMessage,
    final QuotationMessage serviceUnavailableMessage,
    final Object identifier,
    final String notFoundLog,
    final String serviceErrorLog) {
    try {
      final ResponseEntity<GenericResponse<T>> response = supplier.get();
      if (response.getBody() == null || response.getStatusCode().isError()) {
        log.warn(serviceErrorLog, identifier);
        throw new QuotationException(serviceUnavailableMessage);
      }
      return response.getBody().getBody();
    } catch (final FeignException.NotFound ex) {
      log.warn(notFoundLog, identifier);
      throw new QuotationException(notFoundMessage);
    } catch (final FeignException ex) {
      log.error("{}: {}, {}", serviceErrorLog, ex.getMessage(), identifier);
      throw new QuotationException(serviceUnavailableMessage);
    }
  }

  private MaterialResponseDto fetchMaterial(final Integer id) {
    return callFeign(
      () -> this.materialClient.searchMaterialById(id),
      QuotationMessage.MATERIAL_NOT_FOUND,
      QuotationMessage.MATERIAL_SERVICE_UNAVAILABLE,
      id,
      "Material con ID {} no encontrado en el catálogo.",
      "Error técnico al llamar al micro servicio de materiales"
    );
  }

  private CustomerResponseDto fetchCustomer(final String doc) {
    return callFeign(
      () -> this.customerClient.retrieveByDocumentNumber(doc),
      QuotationMessage.CUSTOMER_NOT_FOUND,
      QuotationMessage.CUSTOMER_SERVICE_UNAVAILABLE,
      doc,
      "Customer con DOC {} no encontrado en el servicio de clientes.",
      "Error técnico al llamar al micro servicio de clientes"
    );
  }

  private EmployeeResponseDto fetchEmployee(final String doc) {
    return callFeign(
      () -> this.employeeClient.retrieveByDocumentNumber(doc),
      QuotationMessage.EMPLOYEE_NOT_FOUND,
      QuotationMessage.EMPLOYEE_SERVICE_UNAVAILABLE,
      doc,
      "Employee con DOC {} no encontrado en el servicio de empleados.",
      "Error técnico al llamar al micro servicio de empleados"
    );
  }

  private List<MaterialResponseDto> fetchAllMaterialsByIds(final Set<Integer> ids) {
    return callFeign(
      () -> this.materialClient.retrieveByIdList(ids),
      QuotationMessage.MATERIAL_NOT_FOUND,
      QuotationMessage.MATERIAL_SERVICE_UNAVAILABLE,
      ids,
      "Material con ID {} no encontrado en el catálogo.",
      "Error técnico al llamar al micro servicio de materiales"
    );
  }

  private List<CustomerResponseDto> fetchAllCustomerByDocumentNumberIn(final Set<String> docs) {
    return callFeign(
      () -> this.customerClient.retrieveAllCustomersByDocumentNumber(docs),
      QuotationMessage.CUSTOMER_NOT_FOUND,
      QuotationMessage.CUSTOMER_SERVICE_UNAVAILABLE,
      docs,
      "Customer con DOC {} no encontrado en el servicio de clientes.",
      "Error técnico al llamar al micro servicio de clientes"
    );
  }

  private List<EmployeeResponseDto> fetchEmployeeByDocumentNumberIn(final Set<String> docs) {
    return callFeign(
      () -> this.employeeClient.retrieveAllEmployeesByDocumentNumber(docs),
      QuotationMessage.EMPLOYEE_NOT_FOUND,
      QuotationMessage.EMPLOYEE_SERVICE_UNAVAILABLE,
      docs,
      "Employee con DOC {} no encontrado en el servicio de empleados.",
      "Error técnico al llamar al micro servicio de empleados"
    );
  }
}
