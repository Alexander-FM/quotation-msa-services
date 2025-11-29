package com.codesoft.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientFactory {

  private final WebClient.Builder loadBalancedWebClientBuilder;

  private final WebClient simpleWebClient;

  private final Environment env;

  /**
   * Devuelve un WebClient configurado automáticamente para Local o K8s basado en la clave de la propiedad del servicio.
   *
   * @param serviceUrlKey La clave en application.yml (ej. "MS_CATALOG_ITEM_SERVICE")
   * @param defaultUrl La URL por defecto para local (ej. EmployeeConstants.PORT_API...)
   */
  public WebClient retrieveWebClient(final String serviceUrlKey, final String defaultUrl) {
    String serviceUrl = env.getProperty(serviceUrlKey);
    if (StringUtils.isBlank(serviceUrl)) {
      // --- ESCENARIO LOCAL ---
      log.debug("Factory: Configurando cliente SIMPLE para (Local): {}", defaultUrl);
      return simpleWebClient.mutate()
        .baseUrl(defaultUrl)
        .build();
    } else {
      // --- ESCENARIO KUBERNETES ---
      log.debug("Factory: Configurando cliente LOAD BALANCED para (K8s): {}", serviceUrl);
      return loadBalancedWebClientBuilder
        .baseUrl(serviceUrl)
        .build();
    }
  }
}
