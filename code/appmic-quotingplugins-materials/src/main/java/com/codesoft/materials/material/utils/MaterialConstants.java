package com.codesoft.materials.material.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialConstants {

  public static final String NOT_FOUND_IN_DB_MESSAGE = "Material not found in database";

  public static final String NOT_FOUND_CALCULATION_TYPE = "The calculation type has not been found";

  public static final String FOUND_MESSAGE = "Material found successfully";

  public static final String SAVED_MESSAGE = "Material saved successfully";

  public static final String UPDATED_MESSAGE = "Material updated successfully";

  public static final String REMOVED_MESSAGE = "Material removed successfully";

  public static final String MS_CATALOG_ITEM_SERVICE = "http://appmic-quotingplugins-catalogs/api/catalogs";

  public static final String PORT_API_CATALOG_ITEM_SERVICE = "http://127.0.0.1:8080/api/catalogs";

  public static final String CATALOG_SERVICE_UNAVAILABLE_MESSAGE = "The catalog service is not available (connection refused)";

  public static final int MATERIAL_ERROR_CODE = 71;

  public static final String ALREADY_EXISTS_MESSAGE = "Material already exists in the database";
}
