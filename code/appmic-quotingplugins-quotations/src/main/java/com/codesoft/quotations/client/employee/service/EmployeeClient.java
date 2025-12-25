package com.codesoft.quotations.client.employee.service;

import com.codesoft.quotations.client.employee.dto.EmployeeResponseDto;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${app.external.employee-service-url}")
public interface EmployeeClient {

  @GetMapping("${app.external.employee-service-path}/employee/searchByDocumentNumber/{documentNumber}")
  @CircuitBreaker(name = "ms_employee", fallbackMethod = "fallbackRetrieveByDocumentNumber")
  ResponseEntity<GenericResponse<EmployeeResponseDto>> retrieveByDocumentNumber(
    @PathVariable("documentNumber") final String documentNumber);

  default ResponseEntity<GenericResponse<EmployeeResponseDto>> fallbackRetrieveByDocumentNumber(final String documentNumber, Throwable t) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError(t.getMessage(), null));
  }
}
