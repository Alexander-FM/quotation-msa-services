package com.codesoft.quotations.quotation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuotationConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Quoting Material not found in database";

  public static final String FOUND_MESSAGE = "Quoting Material found successfully";

  public static final String SAVED_MESSAGE = "Quoting Material saved successfully";

  public static final String UPDATED_MESSAGE = "Quoting Material updated successfully";

  public static final String REMOVED_MESSAGE = "Quoting Material removed successfully";

  public static final int QUOTING_ERROR_CODE = 82;

  public static final String ALREADY_EXISTS_MESSAGE = "Quoting Material already exists in the database or conflicts with an existing entry";

  public static final String INTERNAL_SERVICE_ERROR = "An unexpected error occurred while creating the quote.";

  public static final String MATERIAL_SERVICE_UNAVAILABLE_MESSAGE = "The material service is not available (connection refused)";

  public static final String EMPLOYEE_SERVICE_UNAVAILABLE_MESSAGE = "The employee service is not available (connection refused)";

  public static final String CUSTOMER_SERVICE_UNAVAILABLE_MESSAGE = "The customer service is not available (connection refused)";

  public static final String MATERIAL_NOT_FOUND_MESSAGE = "The material requested in the materials microservice has not been found";

  public static final String MODULE_NOT_FOUND_MESSAGE = "The module was not found in the database";

  public static final String EMPLOYEE_NOT_FOUND_MESSAGE = "The employee was not found in the database";

  public static final String CUSTOMER_NOT_FOUND_MESSAGE = "The customer was not found in the database";

}
