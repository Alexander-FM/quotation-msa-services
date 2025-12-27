package com.codesoft.quotations.client.employee.service;

import java.util.List;
import java.util.Set;

import com.codesoft.quotations.client.employee.dto.EmployeeResponseDto;
import com.codesoft.quotations.quotation.utils.QuotationConstants;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${app.external.employee-service-url}", url = "${app.external.employee-service-url}")
public interface EmployeeClient {

  Logger logger = LoggerFactory.getLogger(EmployeeClient.class);

  @GetMapping("${app.external.employee-service-path}/employee/searchByDocumentNumber/{documentNumber}")
  @CircuitBreaker(name = "ms_employee", fallbackMethod = "fallbackRetrieveByDocumentNumber")
  ResponseEntity<GenericResponse<EmployeeResponseDto>> retrieveByDocumentNumber(
    @PathVariable("documentNumber") final String documentNumber);

  default ResponseEntity<GenericResponse<EmployeeResponseDto>> fallbackRetrieveByDocumentNumber(final String documentNumber, Throwable t) {
    if (t instanceof feign.FeignException.NotFound notFound) {
      throw notFound;
    }
    logger.warn("Fallback triggered for retrieveByDocumentNumber with documentNumber: {}. Error: {}", documentNumber, t.getMessage());
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError(QuotationConstants.EMPLOYEE_SERVICE_UNAVAILABLE_MESSAGE, null));
  }

  /**
   * Realiza una llamada al servicio de empleados (findAll) para traer todos los empleados de la base de datos.
   *
   * @param documentNumberList lista de números de documentos de los empleados a buscar
   * @return ResponseEntity<GenericResponse < List < CustomerResponseDto>>> lista de clientes encontrados
   */
  @GetMapping("${app.external.employee-service-path}/employee/searchByDocumentNumberIds")
  @CircuitBreaker(name = "ms_customer", fallbackMethod = "fallbackRetrieveByDocumentNumberList")
  ResponseEntity<GenericResponse<List<EmployeeResponseDto>>> retrieveAllEmployeesByDocumentNumber(
    @RequestParam("documentNumberList") final Set<String> documentNumberList);

  default ResponseEntity<GenericResponse<EmployeeResponseDto>> fallbackRetrieveByDocumentNumberList(
    final Set<String> documentNumberList, Throwable t) {
    if (t instanceof feign.FeignException.NotFound notFound) {
      throw notFound;
    }
    logger.warn("Fallback triggered for retrieveByDocumentNumber with documentNumbers: {}. Error: {}", documentNumberList, t.getMessage());
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError(QuotationConstants.EMPLOYEE_SERVICE_UNAVAILABLE_MESSAGE, null));
  }
}
