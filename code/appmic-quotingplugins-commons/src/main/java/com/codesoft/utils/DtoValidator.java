package com.codesoft.utils;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Un componente de Spring para realizar validaciones programáticas de objetos (DTOs).
 * <p>
 * Esta clase envuelve el {@link Validator} Singleton de Jakarta que gestiona Spring, proporcionando un método de utilidad limpio y de alto
 * rendimiento.
 * <p>
 * Es la forma recomendada de validar objetos <b>dentro de la capa de servicio</b> (por ejemplo, al validar la respuesta de una API externa
 * o antes de una lógica de negocio compleja) donde {@code @Valid} en el controlador no es suficiente.
 *
 * <h3>Ejemplo de Uso en un Servicio:</h3>
 * <p>Simplemente inyecta este bean en tu servicio y llama al método {@code validate()}.</p>
 * <pre>
 * {@code
 * import com.codesoft.utils.DtoValidator; // <-- Importas el nuevo Bean
 *
 * @Service
 * @RequiredArgsConstructor
 * public class MyService {
 *
 * // Spring inyecta este bean (que ya contiene el Validator)
 * private final DtoValidator dtoValidator;
 *
 * public void processExternalData() {
 * ExternalResponseDto responseDto = externalApiClient.getData();
 *
 * // Lanza ConstraintViolationException si la respuesta no es válida
 * // El GlobalExceptionHandler se encargará de atraparla.
 * dtoValidator.validate(responseDto);
 *
 * // ...continuar con la lógica de negocio si la validación pasa
 * }
 * }
 * }
 * </pre>
 *
 * @see jakarta.validation.ConstraintViolationException
 * @see com.codesoft.exception.GlobalExceptionHandler
 */
@Component
@RequiredArgsConstructor
public class DtoValidator {

  /**
   * El validador Singleton de Jakarta, inyectado por Spring.
   */
  private final Validator validator;

  /**
   * Valida un objeto dado contra sus reglas de anotaciones (ej. @NotNull, @NotEmpty).
   * <p>
   * Si el objeto falla la validación, este método lanzará una {@link ConstraintViolationException} que será (o debería ser) manejada por el
   * {@code GlobalExceptionHandler}.
   *
   * @param <T> El tipo genérico del objeto.
   * @param object El objeto (DTO) a validar.
   * @throws ConstraintViolationException sí se encuentran violaciones de validación.
   */
  public <T> void validate(final T object) {
    final Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
