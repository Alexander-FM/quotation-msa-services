package com.codesoft.employees.user.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstants {

  public static final String USER_NOT_FOUND_IN_DB_MESSAGE = "User not found in database";

  public static final String USER_NOT_ACTIVE_MESSAGE = "User found successfully, but is not active";

  public static final String USER_INCORRECT_PASSWORD_MESSAGE = "User found successfully, but password does not match";

  public static final String FOUND_MESSAGE = "User found successfully";

  public static final String SAVED_MESSAGE = "User saved successfully";

  public static final String UPDATED_MESSAGE = "User updated successfully";

  public static final String REMOVED_MESSAGE = "User removed successfully";

  public static final String FIND_ERROR_MESSAGE = "An error occurred while finding the user";
}
