package com.codesoft.quotations.client.customer.service;

import com.codesoft.quotations.client.customer.dto.CustomerResponseDto;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${app.external.customer-service-url}", url = "${app.external.customer-service-url}")
public interface CustomerClient {

  @GetMapping("${app.external.customer-service-path}/customer/searchByDocumentNumber/{documentNumber}")
  @CircuitBreaker(name = "ms_customer", fallbackMethod = "fallbackRetrieveByDocumentNumber")
  ResponseEntity<GenericResponse<CustomerResponseDto>> retrieveByDocumentNumber(@PathVariable("documentNumber") final String documentNumber);

  default ResponseEntity<GenericResponse<CustomerResponseDto>> fallbackRetrieveByDocumentNumber(final String documentNumber, Throwable t) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError(null));
  }
}
