package com.codesoft.quotings.quoting.exception;

import com.codesoft.exception.BaseException;

public class QuotingException extends BaseException {

  public QuotingException(final QuotingMessage message) {
    super(message);
  }
}
