package com.codesoft.catalogs.adjustment_factor.exception;

import com.codesoft.exception.BaseException;

public class AdjustmentFactorException extends BaseException {

  public AdjustmentFactorException(AdjustmentFactorMessage message) {
    super(message); // Pasamos el Enum de empleado al padre
  }
}
