package com.codesoft.customers.customer.exception;

import com.codesoft.customers.customer.utils.CustomerConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CustomerMessage implements IErrorCode {
  CUSTOMER_NOT_FOUND(
    GenericResponseUtils.makeCode(CustomerConstants.CUSTOMER_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND,
    CustomerConstants.NOT_FOUND_IN_DB_MESSAGE),
  CUSTOMER_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(CustomerConstants.CUSTOMER_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT,
    CustomerConstants.ALREADY_EXISTS_MESSAGE),
  CATALOG_ITEM_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(CustomerConstants.CUSTOMER_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 3),
    HttpStatus.SERVICE_UNAVAILABLE,
    CustomerConstants.CATALOG_ITEM_SERVICE_UNAVAILABLE_MESSAGE);

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
