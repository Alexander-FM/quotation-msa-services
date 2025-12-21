package com.codesoft.exception;

import java.net.ConnectException;
import java.nio.channels.UnresolvedAddressException;
import java.util.HashMap;
import java.util.Map;

import com.codesoft.utils.ErrorResponse;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.IErrorCode;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
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

  /**
   * Manejo de errores de validación de datos de entrada. @RequestBody (JSON del Postman).
   *
   * @param ex MethodArgumentNotValidException
   * @return GenericResponse<Object>
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
      errors.put(error.getField(), error.getDefaultMessage())
    );
    return GenericResponseUtils.buildGenericResponseError("Error de validación en el cuerpo", errors);
  }

  /**
   * Manejo de errores de validación de datos de entrada. @PathVariable, @RequestParam, Hibernate etc.
   *
   * @param ex ConstraintViolationException
   * @return GenericResponse<Object>
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<Object> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      // Extrae el nombre del campo del path (ej. "create.requestDto.customerDocumentNumber")
      String fieldName = violation.getPropertyPath().toString();
      errors.put(fieldName, violation.getMessage());
    });
    return GenericResponseUtils.buildGenericResponseError("Violación de restricciones de datos", errors);
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
      GenericResponseUtils.buildGenericResponseWarning(GenericResponseConstants.BAD_REQUEST_MESSAGE), HttpStatus.BAD_REQUEST);
  }

  /**
   * Captura errores de conexión cuando un microservicio está caído o no existe. ResourceAccessException suele envolver a ConnectException o
   * UnresolvedAddressException.
   */
  @ExceptionHandler({ResourceAccessException.class, UnresolvedAddressException.class, ConnectException.class})
  @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
  public GenericResponse<Object> handleConnectionError(final Exception ex) {
    log.warn("ResourceAccessException, UnresolvedAddressException, ConnectException: {}", ex.getMessage());
    final String message = "Gateway Error: El micro servicio destino no está disponible o no responde.";
    return GenericResponseUtils.buildGenericResponseError("Target service unavailable or connection refused via Gateway.", message);
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