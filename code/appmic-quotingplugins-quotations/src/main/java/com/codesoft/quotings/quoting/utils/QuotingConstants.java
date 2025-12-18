package com.codesoft.quotings.quoting.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuotingConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Quoting Material not found in database";

  public static final String FOUND_MESSAGE = "Quoting Material found successfully";

  public static final String SAVED_MESSAGE = "Quoting Material saved successfully";

  public static final String UPDATED_MESSAGE = "Quoting Material updated successfully";

  public static final String REMOVED_MESSAGE = "Quoting Material removed successfully";

  public static final int QUOTING_ERROR_CODE = 82;

  public static final String ALREADY_EXISTS_MESSAGE = "Quoting Material already exists in the database";

  public static final String MATERIAL_SERVICE_UNAVAILABLE_MESSAGE = "The material service is not available (connection refused)";
}
