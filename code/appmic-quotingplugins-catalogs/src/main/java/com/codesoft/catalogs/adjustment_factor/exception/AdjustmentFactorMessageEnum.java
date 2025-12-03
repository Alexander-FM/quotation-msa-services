package com.codesoft.catalogs.adjustment_factor.exception;

import com.codesoft.catalogs.adjustment_factor.utils.AdjustmentFactorConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdjustmentFactorMessageEnum implements IErrorCode {
  ADJUSTMENT_FACTOR_NOT_FOUND(
    GenericResponseUtils.makeCode(AdjustmentFactorConstants.ADJUSTMENT_FACTOR_ERROR_CODE, HttpStatus.NOT_FOUND, 1),
    HttpStatus.NOT_FOUND,
    AdjustmentFactorConstants.NOT_FOUND_MESSAGE),
  ADJUSTMENT_FACTOR_ALREADY_EXISTS(
    GenericResponseUtils.makeCode(AdjustmentFactorConstants.ADJUSTMENT_FACTOR_ERROR_CODE, HttpStatus.CONFLICT, 2),
    HttpStatus.CONFLICT,
    AdjustmentFactorConstants.ALREADY_EXISTS_MESSAGE);

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
