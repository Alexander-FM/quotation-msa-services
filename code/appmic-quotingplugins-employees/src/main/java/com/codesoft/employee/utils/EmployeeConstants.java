package com.codesoft.employee.utils;

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
}
