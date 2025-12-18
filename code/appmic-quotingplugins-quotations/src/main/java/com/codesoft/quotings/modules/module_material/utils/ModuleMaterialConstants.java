package com.codesoft.quotings.modules.module_material.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleMaterialConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Module Material not found in database";

  public static final String FOUND_MESSAGE = "Module Material found successfully";

  public static final String SAVED_MESSAGE = "Module Material saved successfully";

  public static final String UPDATED_MESSAGE = "Module Material updated successfully";

  public static final String REMOVED_MESSAGE = "Module Material removed successfully";

  public static final int MODULE_MATERIAL_ERROR_CODE = 82;

  public static final String MODULE_SERVICE_UNAVAILABLE_MESSAGE = "The module service is not available (connection refused)";

  public static final String ALREADY_EXISTS_MESSAGE = "The materials already exists for that module in the database";
}
