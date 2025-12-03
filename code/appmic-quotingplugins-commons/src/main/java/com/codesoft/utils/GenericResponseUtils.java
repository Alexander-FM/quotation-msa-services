package com.codesoft.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericResponseUtils {

  /**
   * Construye una respuesta genérica de éxito.
   *
   * @param message texto adicional que se concatenará al mensaje por defecto. Si es nulo o vacío se usará
   *   GenericResponseConstants.CORRECT_OPERATION.
   * @param object objeto de payload asociado a la respuesta (puede ser null).
   * @param <T> tipo del objeto de respuesta.
   * @return instancia de GenericResponse<T> con el código de éxito, el mensaje resultante y el objeto.
   */
  public static <T> GenericResponse<T> buildGenericResponseSuccess(final String message, final T object) {
    final String msg = StringUtils.isNotEmpty(message)
      ? StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.CORRECT_OPERATION, message)
      : GenericResponseConstants.CORRECT_OPERATION;
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_OK, msg, object);
  }

  /**
   * Construye una respuesta genérica de éxito.
   *
   * @param object objeto de payload asociado a la respuesta (puede ser null).
   * @param <T> tipo del objeto de respuesta.
   * @return instancia de GenericResponse<T> con el código de éxito, el mensaje resultante y el objeto.
   */
  public static <T> GenericResponse<T> buildGenericResponseSuccess(final T object) {
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_OK, GenericResponseConstants.CORRECT_OPERATION, object);
  }

  /**
   * Construye una respuesta genérica de error.
   *
   * @param message texto adicional que se concatenará al mensaje por defecto. Si es nulo o vacío se usará
   *   GenericResponseConstants.INCORRECT_OPERATION.
   * @param object objeto de payload asociado a la respuesta (puede ser null).
   * @param <T> tipo del objeto de respuesta.
   * @return instancia de GenericResponse<T> con el código de error, el mensaje resultante y el objeto.
   */
  public static <T> GenericResponse<T> buildGenericResponseError(final String message, final T object) {
    final String msg = StringUtils.isNotEmpty(message)
      ? StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, message)
      : GenericResponseConstants.INCORRECT_OPERATION;
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, msg, object);
  }

  /**
   * Construye una respuesta genérica de error.
   *
   * @param object objeto de payload asociado a la respuesta (puede ser null).
   * @param <T> tipo del objeto de respuesta.
   * @return instancia de GenericResponse<T> con el código de error, el mensaje resultante y el objeto.
   */
  public static <T> GenericResponse<T> buildGenericResponseError(final T object) {
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, GenericResponseConstants.INCORRECT_OPERATION, object);
  }

  /**
   * Construye una respuesta genérica de advertencia.
   *
   * @param message texto adicional que se concatenará al mensaje por defecto. Si es nulo o vacío se usará
   *   GenericResponseConstants.WRONG_OPERATION.
   * @param object objeto de payload asociado a la respuesta (puede ser null).
   * @param <T> tipo del objeto de respuesta.
   * @return instancia de GenericResponse<T> con el código de advertencia, el mensaje resultante y el objeto.
   */
  public static <T> GenericResponse<T> buildGenericResponseWarning(final String message, final T object) {
    final String msg = StringUtils.isNotEmpty(message)
      ? StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.WRONG_OPERATION, message)
      : GenericResponseConstants.WRONG_OPERATION;
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_WARNING, msg, object);
  }

  /**
   * Construye una respuesta genérica de advertencia.
   *
   * @param object objeto de payload asociado a la respuesta (puede ser null).
   * @param <T> tipo del objeto de respuesta.
   * @return instancia de GenericResponse<T> con el código de advertencia, el mensaje resultante y el objeto.
   */
  public static <T> GenericResponse<T> buildGenericResponseWarning(final T object) {
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_WARNING, GenericResponseConstants.WRONG_OPERATION, object);
  }

  /**
   * Method to create error codes.
   *
   * @param moduleId Module identifier (2 digits).
   * @param status HTTP status.
   * @param seq Sequence number (3 digits).
   * @return Generated error code.
   */
  public static int makeCode(final int moduleId, final HttpStatus status, final int seq) {
    return moduleId * 1_000_000 + status.value() * 1_000 + seq;
  }
}
