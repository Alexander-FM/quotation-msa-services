package com.codesoft.quotations.quotation.service;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
      log.info("Iniciando llamada al micro servicio de materiales para validar datos relacionados.");
      for (var detail : entityMapped.getDetails()) {
        log.info("Consultando conceptos del modulo con ID: {}", detail.getModule().getId());
        ModuleEntity moduleEntity = this.moduleRepository.findById(detail.getModule().getId())
          .orElseThrow(() -> new QuotationException(QuotationMessage.MODULE_NOT_FOUND));
        log.info("Módulo encontrado: {}", moduleEntity);
        detail.setModule(moduleEntity);
        detail.setOverheadsPercentage(moduleEntity.getOverheadsCostPercentage());
        detail.setOverheadsAmount(moduleEntity.getOverheadsCostPercentage());
        detail.setFeePercentage(moduleEntity.getFeePercentage());
        detail.setFeeAmount(moduleEntity.getFeePercentage());
        detail.setRebatePercentage(moduleEntity.getRebatePercentage());
        detail.setRebateAmount(moduleEntity.getRebatePercentage());
        detail.setRebatePercentage(moduleEntity.getRebatePercentage());
        detail.setRebateAmount(moduleEntity.getRebatePercentage());
        log.info("Validando materiales para los subítems del detalle del módulo con ID: {}", detail.getId());
        for (var subItem : detail.getSubItems()) {
          final MaterialResponseDto materialResponse = fetchMaterial(subItem.getMaterialId());
          log.info("Material encontrado: {}", materialResponse);
          subItem.setMaterialId(materialResponse.getId());
          subItem.calculateTotalPrice();
          subItem.calculateUnitPrice();
        }
      }
      log.info("Mapped quotation entity: {}", entityMapped);
      //final QuotationEntity entity = this.quotationRepository.save(entityMapped);
      //return this.quotationFieldsMapper.toDto(entity);
      return null;
    } catch (final DataIntegrityViolationException ex) {
      log.warn("Data integrity violation when creating quotation: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.QUOTATION_ALREADY_EXISTS);
    } catch (final Exception ex) {
      log.error("Error calling Material microservice: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.QUOTATION_INTERNAL_ERROR);
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
