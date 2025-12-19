package com.codesoft.quotations.quotation.exception;

import com.codesoft.exception.BaseException;

public class QuotationException extends BaseException {

  public QuotationException(final QuotationMessage message) {
    super(message);
  }
}
