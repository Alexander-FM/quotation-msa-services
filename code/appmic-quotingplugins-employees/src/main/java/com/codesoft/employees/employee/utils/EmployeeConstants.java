package com.codesoft.employees.employee.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Employee not found in database";

  public static final String FOUND_MESSAGE = "Employee found successfully";

  public static final String SAVED_MESSAGE = "Employee saved successfully";

  public static final String UPDATED_MESSAGE = "Employee updated successfully";

  public static final String REMOVED_MESSAGE = "Employee removed successfully";

  public static final String MS_CATALOG_ITEM_SERVICE = "http://appmic-quotingplugins-catalogs/api/catalogs";

  public static final String PORT_API_CATALOG_ITEM_SERVICE = "http://127.0.0.1:8080/api/catalogs";

  public static final String CATALOG_ITEM_SERVICE_UNAVAILABLE_MESSAGE = "The catalog item service is not available (connection refused)";

  public static final int EMPLOYEE_ERROR_CODE = 51;

  public static final String EMPLOYEE_CONFLICT_MESSAGE =
    "The employee already exists or violated a restriction that prevented the process from being completed.";

  public static final String USER_NOT_FOUND_MESSAGE = "Associated user not found in database";

  public static final String EMPLOYEE_USER_CONFLICT_MESSAGE = "The user is already associated with another employee.";
}
