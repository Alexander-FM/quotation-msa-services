package com.codesoft.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseErrorMessage {
  BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, GenericResponseConstants.BAD_REQUEST),
  ID_PROVIDED_ON_CREATE(HttpServletResponse.SC_BAD_REQUEST, GenericResponseConstants.ID_PROVIDED_ON_CREATE),
  UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, GenericResponseConstants.UNAUTHORIZED),
  ACCESS_DENIED(HttpServletResponse.SC_FORBIDDEN, GenericResponseConstants.ACCESS_DENIED),
  NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, GenericResponseConstants.NOT_FOUND),
  ERROR_INTERNAL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, GenericResponseConstants.ERROR_INTERNAL),
  SERVICE_NOT_AVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE, GenericResponseConstants.UNAVAILABLE_SERVICE);

  private final Integer errorCode;

  private final String errorMessage;
}
