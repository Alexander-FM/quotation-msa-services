package com.codesoft.materials.material.exception;

import com.codesoft.materials.material.utils.MaterialConstants;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MaterialMessage implements IErrorCode {
  MATERIAL_NOT_FOUND(
    GenericResponseUtils.makeCode(MaterialConstants.MATERIAL_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND,
    MaterialConstants.NOT_FOUND_IN_DB_MESSAGE),
  MATERIAL_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(MaterialConstants.MATERIAL_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT,
    MaterialConstants.ALREADY_EXISTS_MESSAGE),
  CATALOG_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_MATERIALS_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 3),
    HttpStatus.SERVICE_UNAVAILABLE,
    MaterialConstants.CATALOG_SERVICE_UNAVAILABLE_MESSAGE),
  MATERIAL_CALCULATION_TYPE_NOT_FOUND(GenericResponseUtils.makeCode(MaterialConstants.MATERIAL_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND, MaterialConstants.NOT_FOUND_CALCULATION_TYPE);

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
