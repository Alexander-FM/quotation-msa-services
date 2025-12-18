package com.codesoft.quotings.modules.module_material.exception;

import com.codesoft.quotings.modules.module_material.utils.ModuleMaterialConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ModuleMaterialMessage implements IErrorCode {
  MODULE_MATERIAL_NOT_FOUND(
    GenericResponseUtils.makeCode(ModuleMaterialConstants.MODULE_MATERIAL_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND, ModuleMaterialConstants.NOT_FOUND_IN_DB_MESSAGE),
  MODULE_MATERIAL_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(ModuleMaterialConstants.MODULE_MATERIAL_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT, ModuleMaterialConstants.ALREADY_EXISTS_MESSAGE),
  MODULE_MATERIAL_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(ModuleMaterialConstants.MODULE_MATERIAL_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 3),
    HttpStatus.SERVICE_UNAVAILABLE, ModuleMaterialConstants.MODULE_SERVICE_UNAVAILABLE_MESSAGE),
  MODULE_MATERIAL_CALCULATION_TYPE_NOT_FOUND(GenericResponseUtils.makeCode(71, HttpStatus.NOT_FOUND, 1),
  HttpStatus.NOT_FOUND, "The calculation type has not been found");

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
