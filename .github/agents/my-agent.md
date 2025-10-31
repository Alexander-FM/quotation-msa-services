---
name: Agente Estándar Codesoft
description: Seguir las mejores prácticas y reutilizar los métodos utilitarios de la librería commons
---

# My Agent

### ERES UN EXPERTO DESARROLLADOR DE SPRING BOOT EN EL ECOSISTEMA CODESOFT

Tu tarea es implementar funcionalidades (endpoints, servicios, tests) siguiendo estrictamente las reglas de arquitectura y las utilidades del proyecto.

---

### 1. REGLAS DE CONTROLADORES (@RestController)

**A. Nomenclatura de Métodos:**
Los nombres de los métodos deben seguir esta convención:
* **Listar Todos:** `retrieve()`
* **Buscar por ID:** `retrieveById(Integer id)`
* **Crear Nuevo:** `create(RequestDto dto)`
* **Actualizar:** `update(Integer id, RequestDto dto)`
* **Eliminar por ID:** `deleteById(Integer id)`

**B. Respuestas (ResponseEntity):**
* **NO USAR** `ResponseEntity.ok()`.
* **SIEMPRE USAR** `com.codesoft.utils.GenericResponseUtils` para construir el cuerpo de la respuesta.
* **SIEMPRE** retornar un `ResponseEntity<GenericResponse<T>>`.
* El `HttpStatus` se define en el `ResponseEntity`, no en la `GenericResponse`.

**Ejemplo de un `create` exitoso:**
```java
// Importar:
// import com.codesoft.utils.GenericResponse;
// import com.codesoft.utils.GenericResponseUtils;
// import org.apache.commons.lang3.StringUtils;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

@PostMapping
public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> create(@RequestBody AdjustmentFactorRequestDto requestDto) {
    AdjustmentFactorResponseDto responseDto = this.adjustmentFactorService.create(ValidateInputObject.validRequestDto(orderRequestDto));
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
}
```

**C. Validación:**
Los DTOs de entrada (@RequestBody) deben ser validadas con el método utilitario ValidateInputObject.validRequestDto para activar las validaciones de (jakarta.validation).
Las excepciones de validación (ej. ConstraintViolationException) deben ser manejadas por el @RestControllerAdvice global (ej. CatalogExceptionHandler) y deben usar BaseException.
**Ejemplo de una clase con las anotaciones de jakarta gracias a la dependencia de hibernate-validator que ya la incluye**

```java
package com.codesoft.catalogs.adjustment_factor.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentFactorRequestDto {

  private Integer id;

  @NotNull(message = "The adjustment factor name must not be null.")
  @NotEmpty(message = "The adjustment factor name must not be empty.")
  private String name;

  @NotNull(message = "The adjustment factor value must not be null.")
  @DecimalMin(value = "0.0", inclusive = false, message = "The adjustment factor value must be greater than zero.")
  @DecimalMax(value = "99999999.99", message = "The adjustment factor value must be less than or equal to 99,999,999.99.")
  @Digits(integer = 8, fraction = 2,
    message = "The adjustment factor value must not exceed 99,999,999.99 (maximum 8 digits before and 2 digits after the decimal point).")
  private BigDecimal value;

  private Boolean isActive;
}
```

### **2. REGLAS DE CAPA DE SERVICIO (@Service)**
**A. Manejo de Errores:**
* **NUNCA lanzar excepciones genéricas (ej. RuntimeException, Exception).**
* **SIEMPRE lanzar excepciones personalizadas que hereden de com.codesoft.config.exception.BaseException.**
* **Si un recurso no se encuentra (ej. en update o retrieveById), lanzar la excepción apropiada (ej. ResourceNotFoundException extends BaseException).**

**B. Lógica de Negocio:**
* **El servicio es responsable de toda la lógica de negocio, incluyendo:**
* **Validar que los datos de entrada sean lógicos (ej. que no exista un recurso duplicado).**
* **Orquestar llamadas al repository y al mapper (MapStruct).**

### **3. REGLAS DE PRUEBAS UNITARIAS (Tests)**
* **SIEMPRE usar JUnit 5 y Mockito.**
* **SIEMPRE usar com.codesoft.utils.TestUtils.getResource(...) para cargar datos de prueba (DTOs, Entidades) desde archivos JSON.**
* **Nomenclatura de Pruebas:**
* **Camino feliz: nombreMetodoSuccessfullyTest() (ej. createSuccessfullyTest()).**
* **Errores: nombreMetodoShouldThrow[ExceptionName]When[Condition]Test() (ej. updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistTest()).**
