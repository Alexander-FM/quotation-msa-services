package com.codesoft.quotations.quotation.exception;

import com.codesoft.quotations.quotation.utils.QuotationConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum QuotationMessage implements IErrorCode {
  QUOTATION_NOT_FOUND(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND, QuotationConstants.NOT_FOUND_IN_DB_MESSAGE),
  QUOTATION_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT, QuotationConstants.ALREADY_EXISTS_MESSAGE),
  MATERIAL_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 3),
    HttpStatus.SERVICE_UNAVAILABLE, QuotationConstants.MATERIAL_SERVICE_UNAVAILABLE_MESSAGE);

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
