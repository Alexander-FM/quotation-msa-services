package com.codesoft.catalogs.adjustment_factor.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdjustmentFactorConstants {

  public static final String FOUND_MESSAGE = "Adjustment factor found successfully";

  public static final String SAVED_MESSAGE = "Adjustment factor saved successfully";

  public static final String UPDATED_MESSAGE = "Adjustment factor updated successfully";

  public static final String REMOVED_MESSAGE = "Adjustment factor removed successfully";

  public static final String NOT_FOUND_MESSAGE = "The adjustment factor has not been found in the database.";

  public static final String ALREADY_EXISTS_MESSAGE = "The adjustment factor already exists in the database.";

  public static final int ADJUSTMENT_FACTOR_ERROR_CODE = 31;
}
