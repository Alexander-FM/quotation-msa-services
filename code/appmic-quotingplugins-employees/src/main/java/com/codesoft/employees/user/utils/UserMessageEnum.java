package com.codesoft.employees.user.utils;

import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserMessageEnum implements IErrorCode {
  USER_NOT_FOUND(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CUSTOMERS_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND, UserConstants.USER_NOT_FOUND_IN_DB_MESSAGE), // 404: No existe el usuario
  USER_NOT_ACTIVE(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CUSTOMERS_ERROR_CODE, HttpStatus.FORBIDDEN, 2),
    HttpStatus.FORBIDDEN, UserConstants.USER_NOT_ACTIVE_MESSAGE), // 403: Usuario inactivo
  USER_INCORRECT_PASSWORD(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CUSTOMERS_ERROR_CODE, HttpStatus.UNAUTHORIZED, 3),
    HttpStatus.UNAUTHORIZED, UserConstants.USER_INCORRECT_PASSWORD_MESSAGE); // 401: Contraseña incorrecta

  private final int code;

  private final HttpStatus status;

  private final String message;

  @Override
  public String getErrorMessage() {
    return message;
  }

  @Override
  public Integer getErrorCode() {
    return code;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return status;
  }
}
