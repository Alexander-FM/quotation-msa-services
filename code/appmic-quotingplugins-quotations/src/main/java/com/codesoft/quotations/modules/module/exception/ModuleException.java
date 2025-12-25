package com.codesoft.quotations.modules.module.exception;

import com.codesoft.exception.BaseException;

public class ModuleException extends BaseException {

  public ModuleException(final ModuleMessage message) {
    super(message);
  }
}
