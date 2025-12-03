package com.codesoft.catalogs.catalog_item.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogItemConstants {

  public static final String FOUND_MESSAGE = "Catalog item found successfully";

  public static final String SAVED_MESSAGE = "Catalog item saved successfully";

  public static final String UPDATED_MESSAGE = "Catalog item updated successfully";

  public static final String REMOVED_MESSAGE = "Catalog item removed successfully";

  public static final String NOT_FOUND_MESSAGE = "The catalog item has not been found in the database.";

  public static final String ALREADY_EXISTS_MESSAGE = "The catalog item already exists in the database.";

  public static final int CATALOG_ITEM_ERROR_CODE = 32;
}
