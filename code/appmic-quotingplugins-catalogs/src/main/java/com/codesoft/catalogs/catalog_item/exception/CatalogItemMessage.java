package com.codesoft.catalogs.catalog_item.exception;

import com.codesoft.catalogs.catalog_item.utils.CatalogItemConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CatalogItemMessage implements IErrorCode {
  CATALOG_ITEM_NOT_FOUND(
    GenericResponseUtils.makeCode(CatalogItemConstants.CATALOG_ITEM_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND,
    CatalogItemConstants.NOT_FOUND_MESSAGE),
  CATALOG_ITEM_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(CatalogItemConstants.CATALOG_ITEM_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT,
    CatalogItemConstants.ALREADY_EXISTS_MESSAGE);

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
