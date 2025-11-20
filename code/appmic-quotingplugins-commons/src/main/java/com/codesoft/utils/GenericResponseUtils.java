package com.codesoft.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericResponseUtils {

  /**
   * Method use to build generic response success.
   *
   * @return GenericResponse The generic response of any type.
   */
  public static <T> GenericResponse<T> buildGenericResponseSuccess(final String message, final T object) {
    final String msg = StringUtils.isNotEmpty(message)
      ? StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.CORRECT_OPERATION, message)
      : GenericResponseConstants.CORRECT_OPERATION;
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_OK, msg, object);
  }

  /**
   * Method use to build generic response error.
   *
   * @return GenericResponse The generic response of any type.
   */
  public static <T> GenericResponse<T> buildGenericResponseError(final String message, final T object) {
    final String msg = StringUtils.isNotEmpty(message)
      ? StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION, message)
      : GenericResponseConstants.INCORRECT_OPERATION;
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, msg, object);
  }

  /**
   * Method use to build generic response warning.
   *
   * @return GenericResponse The generic response of any type.
   */
  public static <T> GenericResponse<T> buildGenericResponseWarning(final String message, final T object) {
    final String msg = StringUtils.isNotEmpty(message)
      ? StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.WRONG_OPERATION, message)
      : GenericResponseConstants.WRONG_OPERATION;
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_WARNING, msg, object);
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
