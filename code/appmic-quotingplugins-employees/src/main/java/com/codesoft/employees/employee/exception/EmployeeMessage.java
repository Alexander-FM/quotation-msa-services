package com.codesoft.employees.employee.exception;

import com.codesoft.employees.employee.utils.EmployeeConstants;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum EmployeeMessage implements IErrorCode {
  EMPLOYEE_NOT_FOUND(
    GenericResponseUtils.makeCode(EmployeeConstants.EMPLOYEE_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND,
    EmployeeConstants.NOT_FOUND_IN_DB_MESSAGE),
  EMPLOYEE_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(EmployeeConstants.EMPLOYEE_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT,
    EmployeeConstants.ALREADY_EXISTS_MESSAGE),
  CATALOG_ITEM_SERVICE_UNAVAILABLE(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_EMPLOYEES_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 3),
    HttpStatus.SERVICE_UNAVAILABLE,
    EmployeeConstants.CATALOG_ITEM_SERVICE_UNAVAILABLE_MESSAGE);

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
