package com.codesoft.catalogs.catalog_item.exception;

import com.codesoft.catalogs.adjustment_factor.exception.AdjustmentFactorMessageEnum;
import com.codesoft.exception.BaseException;

public class CatalogItemException extends BaseException {

  public CatalogItemException(AdjustmentFactorMessageEnum message) {
    super(message); // Pasamos el Enum de empleado al padre
  }
}
