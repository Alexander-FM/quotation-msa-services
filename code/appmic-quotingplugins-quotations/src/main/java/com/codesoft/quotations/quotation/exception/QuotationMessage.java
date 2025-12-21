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
  QUOTATION_INTERNAL_ERROR(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR, 3),
    HttpStatus.INTERNAL_SERVER_ERROR, QuotationConstants.INTERNAL_SERVICE_ERROR),
  MODULE_NOT_FOUND(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.NOT_FOUND, 4),
    HttpStatus.NOT_FOUND, QuotationConstants.MODULE_NOT_FOUND_MESSAGE),
  MATERIAL_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 5),
    HttpStatus.SERVICE_UNAVAILABLE, QuotationConstants.MATERIAL_SERVICE_UNAVAILABLE_MESSAGE),
  MATERIAL_NOT_FOUND(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.NOT_FOUND, 6),
    HttpStatus.NOT_FOUND, QuotationConstants.MATERIAL_NOT_FOUND_MESSAGE),
  EMPLOYEE_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 7),
    HttpStatus.SERVICE_UNAVAILABLE, QuotationConstants.EMPLOYEE_SERVICE_UNAVAILABLE_MESSAGE),
  EMPLOYEE_NOT_FOUND(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.NOT_FOUND, 6),
    HttpStatus.NOT_FOUND, QuotationConstants.EMPLOYEE_NOT_FOUND_MESSAGE),
  CUSTOMER_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 8),
    HttpStatus.SERVICE_UNAVAILABLE, QuotationConstants.CUSTOMER_SERVICE_UNAVAILABLE_MESSAGE),
  CUSTOMER_NOT_FOUND(
    GenericResponseUtils.makeCode(QuotationConstants.QUOTING_ERROR_CODE, HttpStatus.NOT_FOUND, 6),
    HttpStatus.NOT_FOUND, QuotationConstants.CUSTOMER_NOT_FOUND_MESSAGE);

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
