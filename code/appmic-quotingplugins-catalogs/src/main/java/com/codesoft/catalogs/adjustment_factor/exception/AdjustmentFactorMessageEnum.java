package com.codesoft.catalogs.adjustment_factor.exception;

import com.codesoft.catalogs.adjustment_factor.utils.AdjustmentFactorConstants;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdjustmentFactorMessageEnum implements IErrorCode {
  ADJUSTMENT_FACTOR_NOT_FOUND(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CATALOGS_ERROR_CODE, HttpStatus.NOT_FOUND, 1), HttpStatus.NOT_FOUND,
    AdjustmentFactorConstants.FIND_ERROR_MESSAGE),
  ADJUSTMENT_FACTOR_SAVE_ERROR(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CATALOGS_ERROR_CODE, HttpStatus.NOT_FOUND, 1), HttpStatus.NOT_FOUND,
    AdjustmentFactorConstants.SAVE_ERROR_MESSAGE),
  ADJUSTMENT_FACTOR_UPDATE_ERROR(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CATALOGS_ERROR_CODE, HttpStatus.NOT_FOUND, 1), HttpStatus.NOT_FOUND,
    AdjustmentFactorConstants.UPDATE_ERROR_MESSAGE),
  ADJUSTMENT_FACTOR_DELETE_ERROR(
    GenericResponseUtils.makeCode(GenericResponseConstants.APPMIC_CATALOGS_ERROR_CODE, HttpStatus.NOT_FOUND, 1), HttpStatus.NOT_FOUND,
    AdjustmentFactorConstants.DELETE_ERROR_MESSAGE);

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
