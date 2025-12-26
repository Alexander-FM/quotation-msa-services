package com.codesoft.quotations.client.customer.service;

import com.codesoft.quotations.client.customer.dto.CustomerResponseDto;
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

@FeignClient(name = "${app.external.customer-service-url}")
public interface CustomerClient {

  Logger logger = LoggerFactory.getLogger(CustomerClient.class);

  @GetMapping("${app.external.customer-service-path}/customer/searchByDocumentNumber/{documentNumber}")
  @CircuitBreaker(name = "ms_customer", fallbackMethod = "fallbackRetrieveByDocumentNumber")
  ResponseEntity<GenericResponse<CustomerResponseDto>> retrieveByDocumentNumber(
    @PathVariable("documentNumber") final String documentNumber);

  default ResponseEntity<GenericResponse<CustomerResponseDto>> fallbackRetrieveByDocumentNumber(final String documentNumber, Throwable t) {
    if (t instanceof feign.FeignException.NotFound notFound) {
      throw notFound;
    }
    logger.warn("Fallback triggered for retrieveByDocumentNumber with documentNumber: {}. Error: {}", documentNumber, t.getMessage());
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError(QuotationConstants.CUSTOMER_SERVICE_UNAVAILABLE_MESSAGE, null));
  }
}
