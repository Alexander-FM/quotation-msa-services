package com.codesoft.employees.employee.exception;

import com.codesoft.exception.BaseException;

public class EmployeeException extends BaseException {

  public EmployeeException(EmployeeMessage message) {
    super(message); // Pasamos el Enum de empleado al padre
  }
}
