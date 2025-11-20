package com.codesoft.utils;

import java.util.Map;

import com.codesoft.exception.BaseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
public class WebClientErrorHandler {

  private WebClientErrorHandler() {
  }

  /**
   * Procesa la excepción y devuelve la BaseException adecuada o la específica del servicio.
   *
   * @param ex La excepción original capturada.
   * @param objectMapper Necesario para deserializar el error upstream.
   * @param serviceUnavailableCode El enum de error a usar si el servicio está caído (ej. AUTH_EMPLOYEE_SERVICE_UNAVAILABLE).
   * @return RuntimeException lista para ser lanzada.
   */
  public static RuntimeException handle(final Throwable ex,
    final ObjectMapper objectMapper,
    final IErrorCode serviceUnavailableCode) {

    // 1. Servicio Caído (Connection Refused, Timeout)
    if (ex instanceof WebClientRequestException) {
      log.error("Servicio externo no disponible: {}", ex.getMessage());
      // Aquí decidimos si lanzamos BaseException genérica o una específica
      // Si quieres usar tu AuthException aquí, tendrías que pasar una función,
      // pero lo más estándar en commons es devolver BaseException con el código pasado.
      return new BaseException(serviceUnavailableCode);
    }
    if (ex instanceof WebClientResponseException responseEx) {
      log.warn("Respuesta de error del servicio upstream: {}", responseEx.getResponseBodyAsString());
      return extractUpstreamException(responseEx, objectMapper);
    }
    log.error("Error inesperado en comunicación WebClient", ex);
    return new BaseException(BaseErrorMessage.ERROR_INTERNAL);
  }

  private static BaseException extractUpstreamException(final WebClientResponseException ex, final ObjectMapper objectMapper) {
    try {
      GenericResponse<Map<String, Object>> errorResponse = objectMapper.readValue(
        ex.getResponseBodyAsString(),
        new TypeReference<>() {
        }
      );
      if (errorResponse != null && errorResponse.getBody() != null) {
        Map<String, Object> errorDetails = errorResponse.getBody();
        final Integer originalCode = (Integer) errorDetails.get("errorCode");
        final String originalMessage = (String) errorDetails.get("errorMessage");
        final HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatusCode().value());

        return new BaseException(DynamicErrorCode.fromOriginal(originalCode, originalMessage, httpStatus));
      }
    } catch (final Exception parseEx) {
      log.error("No se pudo parsear el error upstream", parseEx);
    }
    return new BaseException(ex.getStatusCode().is4xxClientError() ?
      BaseErrorMessage.NOT_FOUND : BaseErrorMessage.ERROR_INTERNAL);
  }
}
