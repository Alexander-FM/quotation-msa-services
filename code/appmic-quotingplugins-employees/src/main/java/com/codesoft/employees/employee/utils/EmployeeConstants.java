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

  public static final String FIND_ERROR_MESSAGE = "An error occurred while finding the Employee";

  public static final String SAVE_ERROR_MESSAGE = "An error occurred while saving the Employee";

  public static final String UPDATE_ERROR_MESSAGE = "An error occurred while updating the Employee";

  public static final String DELETE_ERROR_MESSAGE = "An error occurred while deleting the Employee";

  public static final String MS_CATALOG_ITEM_SERVICE = "MS_CATALOG_ITEM_SERVICE";

  public static final String PORT_API_CATALOG_ITEM_SERVICE = "http://127.0.0.1:8080/api/catalogs/catalog-item/searchByDocumentTypeCode";

  public static final String CATALOG_ITEM_SERVICE_UNAVAILABLE_MESSAGE = "The catalog item service is not available (connection refused)";

  public static final int CATALOG_ITEM_ERROR_CODE = 51;

  public static final String NOT_FOUND_MESSAGE = "Employee not found";

  public static final String ALREADY_EXISTS_MESSAGE = "Employee already exists in the database";
}
