package com.codesoft.catalogs.catalog_item.exception;

import com.codesoft.exception.BaseException;

public class CatalogItemException extends BaseException {

  public CatalogItemException(CatalogItemMessage message) {
    super(message); // Pasamos el Enum de empleado al padre
  }
}
