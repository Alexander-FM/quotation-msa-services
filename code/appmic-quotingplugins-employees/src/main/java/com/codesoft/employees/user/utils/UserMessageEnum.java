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
    HttpStatus.NOT_FOUND, UserConstants.NOT_FOUND_IN_DB_MESSAGE),
  USER_NOT_ACTIVE(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CUSTOMERS_ERROR_CODE, HttpStatus.OK, 1),
    HttpStatus.OK, "User found successfully, but is not active"),
  USER_INCORRECT_PASSWORD(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CUSTOMERS_ERROR_CODE, HttpStatus.OK, 1),
    HttpStatus.OK, "User found successfully, but password does not match");

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
