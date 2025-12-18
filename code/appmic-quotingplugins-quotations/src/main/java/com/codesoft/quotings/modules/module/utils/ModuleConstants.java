package com.codesoft.quotings.modules.module.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Module not found in database";

  public static final String FOUND_MESSAGE = "Module found successfully";

  public static final String SAVED_MESSAGE = "Module saved successfully";

  public static final String UPDATED_MESSAGE = "Module updated successfully";

  public static final String REMOVED_MESSAGE = "Module removed successfully";

  public static final int MODULE_ERROR_CODE = 81;

  public static final String ALREADY_EXISTS_MESSAGE = "Module already exists in the database";
}
