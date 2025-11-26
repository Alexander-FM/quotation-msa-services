package com.codesoft.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codesoft.utils.ErrorResponse;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<Object> genericException(final Exception ex) {
    final String message = "An unexpected internal error occurred. The support team has been notified.";
    log.error("Exception: {}", ex.getMessage(), ex);
    return GenericResponseUtils.buildGenericResponseError("Internal Server Error", message);
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
    // Extraemos la lista de errores
    List<String> errorMessages = ex.getBindingResult()
      .getAllErrors()
      .stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage) // "The adjustment factor name must not be empty."
      .toList();
    Map<String, List<String>> listHashMap = Collections.singletonMap("violations", errorMessages);
    log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
    return GenericResponseUtils.buildGenericResponseError("Bad Request", listHashMap);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
  public GenericResponse<Object> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
    final String message = "Method not permitted for this resource.";
    log.warn("HttpRequestMethodNotSupportedException: {}", ex.getMessage());
    return GenericResponseUtils.buildGenericResponseError("Method Not Allowed", message);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public GenericResponse<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
    final String message = "The requested resource was not found.";
    log.warn("NoHandlerFoundException: {}", ex.getMessage());
    return GenericResponseUtils.buildGenericResponseError("Not Found", message);
  }

  /**
   * Manejo especial para excepciones lanzadas durante el parsing JSON (Por ejemplo, en @JsonCreator In EnumClass).
   *
   * @param ex the exception
   * @return the response entity
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<GenericResponse<Object>> handleJsonParseException(HttpMessageNotReadableException ex) {
    Throwable cause = ex.getCause();
    if (cause instanceof ValueInstantiationException) {
      Throwable innerCause = cause.getCause();
      if (innerCause instanceof BaseException baseException) {
        return handleGlobalException(baseException);
      }
    }
    return new ResponseEntity<>(
      GenericResponseUtils.buildGenericResponseWarning("Malformed JSON Request", "The request body is invalid or cannot be parsed."),
      HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<GenericResponse<Object>> handleGlobalException(final BaseException ex) {
    log.warn("BaseException: {}", ex.getMessage());
    IErrorCode error = ex.getErrorCodeInterface();
    HttpStatus status = error.getHttpStatus();
    return new ResponseEntity<>(GenericResponseUtils.buildGenericResponseError(
      new ErrorResponse(error.getErrorCode(), error.getErrorMessage())), status);
  }
}