package com.codesoft.role.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Role not found in database";

  public static final String FOUND_MESSAGE = "Role found successfully";

  public static final String SAVED_MESSAGE = "Role saved successfully";

  public static final String UPDATED_MESSAGE = "Role updated successfully";

  public static final String REMOVED_MESSAGE = "Role removed successfully";

  public static final String FIND_ERROR_MESSAGE = "An error occurred while finding the Role";

  public static final String SAVE_ERROR_MESSAGE = "An error occurred while saving the Role";

  public static final String UPDATE_ERROR_MESSAGE = "An error occurred while updating the Role";

  public static final String DELETE_ERROR_MESSAGE = "An error occurred while deleting the Role";
}
