package com.codesoft.catalogs.adjustment_factor.exception;

import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdjustmentFactorMessage implements IErrorCode {
  ADJUSTMENT_FACTOR_ALREADY_EXISTS(1001, HttpStatus.CONFLICT, "El ajuste de factor ya existe.");

  private final int code;

  private final HttpStatus status;

  private final String message;

  @Override
  public String getErrorMessage() {
    return message;
  }

  @Override
  public Integer getErrorCode() {
    return code;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return status;
  }
}
