package com.codesoft.employees.employee.client.catalog_item.service;

import java.util.Objects;

import com.codesoft.config.WebClientFactory;
import com.codesoft.employees.employee.client.catalog_item.dto.CatalogItemResponseDto;
import com.codesoft.employees.employee.exception.EmployeeMessage;
import com.codesoft.employees.employee.utils.EmployeeConstants;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.WebClientErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class CatalogItemClienteImpl implements CatalogItemClient {

  private final WebClient webClient;

  private final WebClientErrorHandler errorHandler;

  public CatalogItemClienteImpl(final WebClientFactory webClientFactory, WebClientErrorHandler errorHandler) {
    this.webClient = webClientFactory.retrieveWebClient(
      EmployeeConstants.MS_CATALOG_ITEM_SERVICE,
      EmployeeConstants.PORT_API_CATALOG_ITEM_SERVICE
    );
    this.errorHandler = errorHandler;
  }

  @Override
  public CatalogItemResponseDto searchByDocumentTypeCode(final String code) {
    try {
      final GenericResponse<CatalogItemResponseDto> response = webClient.get()
        .uri("/catalog-item/searchByDocumentTypeCode", uriBuilder -> uriBuilder
          .queryParam("code", code) // Solo enviamos username
          .build())
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

}
