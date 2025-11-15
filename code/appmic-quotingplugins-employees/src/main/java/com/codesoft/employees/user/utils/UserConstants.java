package com.codesoft.employees.user.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "User not found in database";

  public static final String FOUND_MESSAGE = "User found successfully";

  public static final String SAVED_MESSAGE = "User saved successfully";

  public static final String UPDATED_MESSAGE = "User updated successfully";

  public static final String REMOVED_MESSAGE = "User removed successfully";

  public static final String FIND_ERROR_MESSAGE = "An error occurred while finding the user";

  public static final String SAVE_ERROR_MESSAGE = "An error occurred while saving the user";

  public static final String UPDATE_ERROR_MESSAGE = "An error occurred while updating the user";

  public static final String DELETE_ERROR_MESSAGE = "An error occurred while deleting the user";
}
