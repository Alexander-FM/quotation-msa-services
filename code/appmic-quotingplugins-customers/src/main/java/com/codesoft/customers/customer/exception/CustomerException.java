package com.codesoft.customers.customer.exception;

import com.codesoft.exception.BaseException;

public class CustomerException extends BaseException {

  public CustomerException(final CustomerMessage message) {
    super(message);
  }
}
