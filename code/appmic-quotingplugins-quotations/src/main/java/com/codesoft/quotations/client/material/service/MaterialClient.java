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

@FeignClient(name = "${app.external.material-service-url}", url = "${app.external.material-service-url}")
public interface MaterialClient {

  @GetMapping("${app.external.material-service-path}/material/{id}")
  @CircuitBreaker(name = "ms_material", fallbackMethod = "fallbackSearchMaterialById")
  ResponseEntity<GenericResponse<MaterialResponseDto>> searchMaterialById(@PathVariable("id") final Integer id);

  default ResponseEntity<GenericResponse<MaterialResponseDto>> fallbackSearchMaterialById(final Integer id, Throwable t) {
    // Si la excepción es un 404, la relanzamos para que llegue al catch del Service
    if (t instanceof feign.FeignException.NotFound notFound) {
      throw notFound;
    }
    // Para cualquier otro error (500, Timeout, etc.), sí devolvemos el 503
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(GenericResponseUtils.buildGenericResponseError("Servicio de materiales no disponible temporalmente", null));
  }
}
