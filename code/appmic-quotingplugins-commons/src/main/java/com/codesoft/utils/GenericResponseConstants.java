package com.codesoft.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericResponseConstants {

  public static final int RESPONSE_OK = 1;

  public static final int RESPONSE_WARNING = 0;

  public static final int RESPONSE_ERROR = -1;

  public static final String CORRECT_OPERATION = "Operation completed successfully";

  public static final String INCORRECT_OPERATION = "The operation could not be completed";

  public static final String WRONG_OPERATION = "An error occurred while performing the operation";

  public static final String CONFLICT = "The resource already exists in the database";

  public static final String UNAVAILABLE_SERVICE = "The service is not available, please try again later.";

  public static final String VIEW = "/view";

  public static final String DASH = " - ";

  public static final String COLON = ": ";

  public static final String PERIOD = ".";

  public static final String COMMA = ", ";

  public static final String SPACE = " ";
}
