package com.codesoft.catalogs.catalog_item.exception;

import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CatalogItemMessage implements IErrorCode {
  CATALOG_ITEM_ALREADY_EXISTS(1001, HttpStatus.CONFLICT, "El catalogo item ya existe.");

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
