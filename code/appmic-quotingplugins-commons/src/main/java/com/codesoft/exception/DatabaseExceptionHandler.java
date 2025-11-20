package com.codesoft.exception;

import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador de excepciones especifico para errores de base de datos. <p>Esta clase se carga condicionalmente solo si la clase
 * DataIntegrityViolationException está presente en el classpath.</p>
 */
@RestControllerAdvice
@Log4j2
@ConditionalOnClass(DataIntegrityViolationException.class)
public class DatabaseExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(code = HttpStatus.CONFLICT)
  public GenericResponse<Object> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
    final String message = "Conflict error. A record with this data already exists, or a data restriction was violated.";
    log.error("DataIntegrityViolationException: {}", ex.getMostSpecificCause().getMessage());
    return GenericResponseUtils.buildGenericResponseError("Duplicate Data", message);
  }
}
