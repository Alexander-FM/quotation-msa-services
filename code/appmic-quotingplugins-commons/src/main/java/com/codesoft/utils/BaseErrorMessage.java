package com.codesoft.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseErrorMessage implements IErrorCode {
  BAD_REQUEST(GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.BAD_REQUEST, 1),
    HttpStatus.BAD_REQUEST),
  ID_PROVIDED_ON_CREATE(GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.BAD_REQUEST, 2),
    HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.UNAUTHORIZED, 1),
    HttpStatus.UNAUTHORIZED),
  ACCESS_DENIED(GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.FORBIDDEN, 1),
    HttpStatus.FORBIDDEN),
  NOT_FOUND(GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND),
  ERROR_INTERNAL(GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR, 1),
    HttpStatus.INTERNAL_SERVER_ERROR),
  SERVICE_NOT_AVAILABLE(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_COMMONS_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, 1),
    HttpStatus.SERVICE_UNAVAILABLE);

  private final Integer errorCode;

  private final HttpStatus httpStatus;

  @Override
  public String getErrorMessage() {
    return this.name();
  }
}
