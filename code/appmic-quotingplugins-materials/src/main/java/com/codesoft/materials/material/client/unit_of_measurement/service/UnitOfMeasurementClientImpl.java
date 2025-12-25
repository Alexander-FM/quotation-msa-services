package com.codesoft.materials.material.client.unit_of_measurement.service;

import java.util.Objects;

import com.codesoft.config.WebClientFactory;
import com.codesoft.materials.material.client.unit_of_measurement.dto.UnitOfMeasurementResponseDto;
import com.codesoft.materials.material.exception.MaterialMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.WebClientErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class UnitOfMeasurementClientImpl implements UnitOfMeasurementClient {

  private final WebClient webClient;

  private final WebClientErrorHandler errorHandler;

  public UnitOfMeasurementClientImpl(final WebClientFactory webClientFactory, final WebClientErrorHandler errorHandler,
    @Value("${app.external.catalog-service-url}") final String catalogServiceUrl) {
    log.info("Connecting to Catalog Item Service at: {}", catalogServiceUrl);
    this.webClient = webClientFactory.retrieveWebClient(catalogServiceUrl);
    this.errorHandler = errorHandler;
  }

  @Override
  public UnitOfMeasurementResponseDto searchByName(final String name) {
    try {
      String token = getCurrentToken();
      final GenericResponse<UnitOfMeasurementResponseDto> response = webClient.get()
        .uri("/unit-of-measurement/searchByName", uriBuilder -> uriBuilder
          .queryParam("name", name) // Solo enviamos username
          .build())
        .header(HttpHeaders.AUTHORIZATION, token)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<GenericResponse<UnitOfMeasurementResponseDto>>() {
        })
        .block();
      if (Objects.nonNull(response) && ObjectUtils.isNotEmpty(response.getBody())) {
        return response.getBody();
      }
      return null;
    } catch (final Exception ex) {
      throw errorHandler.handle(ex, MaterialMessage.CATALOG_SERVICE_UNAVAILABLE);
    }
  }

  private String getCurrentToken() {
    try {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
        String authHeader = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          return authHeader;
        }
      }
    } catch (final Exception e) {
      log.warn("No se pudo obtener el token del contexto actual");
    }
    return null;
  }

}
