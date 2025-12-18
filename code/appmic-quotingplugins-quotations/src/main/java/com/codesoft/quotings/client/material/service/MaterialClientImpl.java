package com.codesoft.quotings.client.material.service;

import com.codesoft.quotings.client.material.dto.MaterialResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
public class MaterialClientImpl implements MaterialClient {

  @Override
  public MaterialResponseDto searchByName(final String name) {
    return null;
  }

  /**
   * Obtiene el token de autorización del contexto actual de la solicitud.
   *
   * @return El token de autorización si está presente, de lo contrario null.
   */
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
