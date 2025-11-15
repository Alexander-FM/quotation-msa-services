package com.codesoft.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseErrorMessage implements IErrorCode {
  BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST),
  ID_PROVIDED_ON_CREATE(HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED),
  ACCESS_DENIED(HttpServletResponse.SC_FORBIDDEN, HttpStatus.FORBIDDEN),
  NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, HttpStatus.NOT_FOUND),
  ERROR_INTERNAL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
  SERVICE_NOT_AVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);

  private final Integer errorCode;

  private final HttpStatus httpStatus; // El status HTTP real

  @Override
  public String getErrorMessage() {
    return this.name();
  }
}
