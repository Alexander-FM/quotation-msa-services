package com.codesoft.materials.material.exception;

import com.codesoft.exception.BaseException;

public class MaterialException extends BaseException {

  public MaterialException(final MaterialMessage message) {
    super(message);
  }
}
