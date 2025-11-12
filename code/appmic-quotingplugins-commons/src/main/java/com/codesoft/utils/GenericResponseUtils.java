package com.codesoft.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
}
