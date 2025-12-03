package com.codesoft.employees.employee.client.catalog_item.service;

import java.util.Objects;

import com.codesoft.config.WebClientFactory;
import com.codesoft.employees.employee.client.catalog_item.dto.CatalogItemResponseDto;
import com.codesoft.employees.employee.exception.EmployeeMessage;
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
public class CatalogItemClienteImpl implements CatalogItemClient {

  private final WebClient webClient;

  private final WebClientErrorHandler errorHandler;

  public CatalogItemClienteImpl(final WebClientFactory webClientFactory, final WebClientErrorHandler errorHandler,
    @Value("${app.external.catalog-service-url}") final String catalogServiceUrl) {
    log.info("Connecting to Catalog Item Service at: {}", catalogServiceUrl);
    this.webClient = webClientFactory.retrieveWebClient(catalogServiceUrl);
    this.errorHandler = errorHandler;
  }

  @Override
  public CatalogItemResponseDto searchByDocumentTypeCode(final String code) {
    try {
      String token = getCurrentToken();
      final GenericResponse<CatalogItemResponseDto> response = webClient.get()
        .uri("/catalog-item/searchByDocumentTypeCode", uriBuilder -> uriBuilder
          .queryParam("code", code) // Solo enviamos username
          .build())
        .header(HttpHeaders.AUTHORIZATION, token)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<GenericResponse<CatalogItemResponseDto>>() {
        })
        .block();
      if (Objects.nonNull(response) && ObjectUtils.isNotEmpty(response.getBody())) {
        return response.getBody();
      }
      return null;
    } catch (final Exception ex) {
      throw errorHandler.handle(ex, EmployeeMessage.CATALOG_ITEM_SERVICE_UNAVAILABLE);
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
