package com.codesoft.customers.customer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConstants {

  public static final int CUSTOMER_ERROR_CODE = 40;

  public static final String NOT_FOUND_MESSAGE = "El cliente no fue encontrado en la base de datos";

  public static final String ALREADY_EXISTS_MESSAGE = "El cliente ya existe en la base de datos";

  public static final String FOUND_MESSAGE = "Cliente encontrado exitosamente";

  public static final String SAVED_MESSAGE = "Cliente guardado exitosamente";

  public static final String UPDATED_MESSAGE = "Cliente actualizado exitosamente";

  public static final String REMOVED_MESSAGE = "Cliente eliminado exitosamente";
}
