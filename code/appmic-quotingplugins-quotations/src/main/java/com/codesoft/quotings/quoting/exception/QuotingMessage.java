package com.codesoft.quotings.quoting.exception;

import com.codesoft.quotings.quoting.utils.QuotingConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum QuotingMessage implements IErrorCode {
  QUOTING_NOT_FOUND(
    GenericResponseUtils.makeCode(QuotingConstants.QUOTING_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND, QuotingConstants.NOT_FOUND_IN_DB_MESSAGE),
  QUOTING_MATERIAL_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(QuotingConstants.QUOTING_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT, QuotingConstants.ALREADY_EXISTS_MESSAGE),
  MATERIAL_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(QuotingConstants.QUOTING_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 3),
    HttpStatus.SERVICE_UNAVAILABLE, QuotingConstants.MATERIAL_SERVICE_UNAVAILABLE_MESSAGE);

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
