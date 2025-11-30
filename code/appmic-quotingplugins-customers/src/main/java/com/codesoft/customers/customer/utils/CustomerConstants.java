package com.codesoft.customers.customer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Customer not found in database";

  public static final String FOUND_MESSAGE = "Customer found successfully";

  public static final String SAVED_MESSAGE = "Customer saved successfully";

  public static final String UPDATED_MESSAGE = "Customer updated successfully";

  public static final String REMOVED_MESSAGE = "Customer removed successfully";

  public static final String MS_CATALOG_ITEM_SERVICE = "MS_CATALOG_ITEM_SERVICE";

  public static final String PORT_API_CATALOG_ITEM_SERVICE = "http://127.0.0.1:8080/api/catalogs/catalog-item/searchByDocumentTypeCode";

  public static final String CATALOG_ITEM_SERVICE_UNAVAILABLE_MESSAGE = "The catalog item service is not available (connection refused)";

  public static final int CUSTOMER_ERROR_CODE = 41;

  public static final String ALREADY_EXISTS_MESSAGE = "Customer already exists in the database";
}
