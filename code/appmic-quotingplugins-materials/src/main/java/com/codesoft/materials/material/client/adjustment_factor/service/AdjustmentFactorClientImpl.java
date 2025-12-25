package com.codesoft.materials.material.client.adjustment_factor.service;

import java.util.Objects;

import com.codesoft.config.WebClientFactory;
import com.codesoft.materials.material.client.adjustment_factor.dto.AdjustmentFactorResponseDto;
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
public class AdjustmentFactorClientImpl implements AdjustmentFactorClient {

  private final WebClient webClient;

  private final WebClientErrorHandler errorHandler;

  public AdjustmentFactorClientImpl(final WebClientFactory webClientFactory, final WebClientErrorHandler errorHandler,
    @Value("${app.external.catalog-service-url}") final String catalogServiceUrl) {
    log.info("Connecting to Catalog Item Service at: {}", catalogServiceUrl);
    this.webClient = webClientFactory.retrieveWebClient(catalogServiceUrl);
    this.errorHandler = errorHandler;
  }

  @Override
  public AdjustmentFactorResponseDto searchByName(final String name) {
    try {
      String token = getCurrentToken();
      final GenericResponse<AdjustmentFactorResponseDto> response = webClient.get()
        .uri("/adjustment-factor/searchByName", uriBuilder -> uriBuilder
          .queryParam("name", name)
          .build())
        .header(HttpHeaders.AUTHORIZATION, token)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<GenericResponse<AdjustmentFactorResponseDto>>() {
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
      log.warn("No se pudo obtener el token del contexto actual: {}", e.getMessage());
    }
    return null;
  }

}
