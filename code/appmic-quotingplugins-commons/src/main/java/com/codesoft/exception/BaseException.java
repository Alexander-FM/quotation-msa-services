package com.codesoft.exception;

import com.codesoft.utils.BaseErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

  private final BaseErrorMessage errorMessage;

  private final Integer errorCode;

  public BaseException(final BaseErrorMessage errorMessage) {
    super(errorMessage.getErrorMessage());
    this.errorMessage = errorMessage;
    this.errorCode = errorMessage.getErrorCode();
  }
}
