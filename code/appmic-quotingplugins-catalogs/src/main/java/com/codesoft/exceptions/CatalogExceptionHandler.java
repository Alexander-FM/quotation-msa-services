package com.codesoft.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codesoft.exception.BaseException;
import com.codesoft.exception.ErrorResponse;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CatalogExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<Object> genericException(final Exception ex) {
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, GenericResponseConstants.WRONG_OPERATION, ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public GenericResponse<Object> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, GenericResponseConstants.WRONG_OPERATION,
      GenericResponseConstants.CONFLICT);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public GenericResponse<Object> constraintValidations(final ConstraintViolationException ex) {
    final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    final List<String> errorMessages = violations.stream()
      .map(ConstraintViolation::getMessage)
      .toList();
    final Map<String, List<String>> listHashMap = Collections.singletonMap("violations", errorMessages);
    return new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, GenericResponseConstants.WRONG_OPERATION, listHashMap);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<GenericResponse<Object>> handleOrderException(final BaseException ex) {
    BaseErrorMessage errorMessage = ex.getErrorMessage();
    HttpStatus status = switch (errorMessage) {
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
      case SERVICE_NOT_AVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
      case ERROR_INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
      case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
    };
    return new ResponseEntity<>(new GenericResponse<>(GenericResponseConstants.RESPONSE_ERROR, GenericResponseConstants.WRONG_OPERATION,
      new ErrorResponse(ex.getErrorCode(), errorMessage.getErrorMessage())), status);
  }
}
