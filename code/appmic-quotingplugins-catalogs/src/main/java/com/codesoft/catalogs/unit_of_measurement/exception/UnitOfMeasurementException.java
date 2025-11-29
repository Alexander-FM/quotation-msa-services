package com.codesoft.catalogs.unit_of_measurement.exception;

import com.codesoft.exception.BaseException;

public class UnitOfMeasurementException extends BaseException {

  public UnitOfMeasurementException(final UnitOfMeasurementMessage message) {
    super(message); // Pasamos el Enum de empleado al padre
  }
}
