package com.codesoft.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConstants {

  public static final long ACCESS_TOKEN_VALIDITY = 3600000L; // 1 Hora

  public static final long REFRESH_TOKEN_VALIDITY = 86400000L; // 24 Horas

  public static final String GENERATED_TOKEN = "Token Generated Correctly";

  public static final String REFRESH_TOKEN_SUCCESS = "Token Refreshed Correctly";

  public static final String VALID_TOKEN = "The token is valid";

  public static final String INVALID_TOKEN = "The token is invalid or has expired.";
}
