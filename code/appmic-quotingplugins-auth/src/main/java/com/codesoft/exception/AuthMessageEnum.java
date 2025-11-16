package com.codesoft.exception;

import com.codesoft.utils.AuthConstants;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthMessageEnum implements IErrorCode {
  AUTH_EMPLOYEE_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_AUTH_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 1),
    HttpStatus.SERVICE_UNAVAILABLE,
    AuthConstants.EMPLOYEE_SERVICE_UNAVAILABLE_MSG);

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
