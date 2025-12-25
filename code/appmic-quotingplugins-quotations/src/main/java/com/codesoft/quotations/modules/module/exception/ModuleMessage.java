package com.codesoft.quotations.modules.module.exception;

import com.codesoft.quotations.modules.module.utils.ModuleConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ModuleMessage implements IErrorCode {
  MODULE_NOT_FOUND(
    GenericResponseUtils.makeCode(ModuleConstants.MODULE_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND,
    ModuleConstants.NOT_FOUND_IN_DB_MESSAGE),
  MODULE_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(ModuleConstants.MODULE_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT,
    ModuleConstants.ALREADY_EXISTS_MESSAGE);

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
