package com.codesoft.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseErrorMessage {
  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401, "The token is invalid or has been modified"),
  NOT_FOUND(404, "The resource ID does not exist in the database"),
  ERROR_INTERNAL(500, "Internal Server Error"),
  SERVICE_NOT_AVAILABLE(503, "The resource could not be verified, the service is not available");

  private final Integer errorCode;

  private final String errorMessage;
}
