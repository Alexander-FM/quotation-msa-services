package com.codesoft.quotations.client.material.service;

import com.codesoft.quotations.client.material.dto.MaterialResponseDto;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${app.feign.material_client}")
public interface MaterialClient {

  @GetMapping("${app.feign.material_path}/{id}")
  @CircuitBreaker(name = "ms-material", fallbackMethod = "fallbackSearchMaterialById")
  ResponseEntity<GenericResponse<MaterialResponseDto>> searchMaterialById(@PathVariable final Integer id);

  default ResponseEntity<GenericResponse<MaterialResponseDto>> fallbackSearchMaterialById(final Integer id) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError(null));
  }
}
