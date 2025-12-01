package com.codesoft.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConstants {

  public static final String MS_AUTH_SERVICE = "http://appmic-quotingplugins-auth:9000/api/employees";

  public static final String EMPLOYEE_SERVICE_UNAVAILABLE_MESSAGE = "Employee service unavailable (connection refused)";

  public static final String NOT_FOUND_MESSAGE = "The user not was found";

  public static final String PORT_API_EMPLOYEE_USER_SERVICE = "http://127.0.0.1:8082/api/employees";

}
