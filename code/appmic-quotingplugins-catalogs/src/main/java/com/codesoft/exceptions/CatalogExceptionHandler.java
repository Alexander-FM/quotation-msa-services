package com.codesoft.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codesoft.exception.BaseException;
import com.codesoft.exception.ErrorResponse;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Log4j2
public class CatalogExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<Object> genericException(final Exception ex) {
    final String message = "An unexpected internal error occurred. The support team has been notified.";
    log.error("Exception: {}", ex.getMessage(), ex);
    return GenericResponseUtils.buildGenericResponseError("Internal Server Error", message);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public GenericResponse<Object> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
    final String message = "Conflict error. A record with this data already exists, or a data restriction was violated.";
    log.error("DataIntegrityViolationException: {}", ex.getMostSpecificCause().getMessage());
    return GenericResponseUtils.buildGenericResponseError("Conflict", message);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public GenericResponse<Object> constraintValidations(final ConstraintViolationException ex) {
    final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    final List<String> errorMessages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .toList();
    final Map<String, List<String>> listHashMap = Collections.singletonMap("violations", errorMessages);
    return GenericResponseUtils.buildGenericResponseError("Bad Request", listHashMap);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
  public GenericResponse<Object> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
    final String message = "Method not permitted for this resource.";
    log.warn("Attempt at an unauthorized method: {}", ex.getMessage());
    return GenericResponseUtils.buildGenericResponseError("Method Not Allowed", message);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public GenericResponse<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
    final String message = "The requested resource was not found.";
    log.warn("Resource not found (404): {}", ex.getMessage());
    return GenericResponseUtils.buildGenericResponseError("Not Found", message);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<GenericResponse<Object>> handleOrderException(final BaseException ex) {
    BaseErrorMessage errorMessage = ex.getErrorMessage();
    HttpStatus status = switch (errorMessage) {
      case BAD_REQUEST, ID_PROVIDED_ON_CREATE -> HttpStatus.BAD_REQUEST;
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
      case SERVICE_NOT_AVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
      case ERROR_INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
      case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
      case ACCESS_DENIED -> HttpStatus.FORBIDDEN;
    };
    return new ResponseEntity<>(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY,
        new ErrorResponse(ex.getErrorCode(), errorMessage.getErrorMessage())), status);
  }
}
