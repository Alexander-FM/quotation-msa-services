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

**Ejemplo de un `CRUD` exitoso:**

```java
@RestController
@RequestMapping("/api/catalogs/catalog-item")
@RequiredArgsConstructor
public class CatalogItemController {

  private final CatalogItemService catalogItemService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<CatalogItemResponseDto>>> retrieve() {
    final List<CatalogItemResponseDto> responseDtoList = catalogItemService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final CatalogItemResponseDto responseDto = catalogItemService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @GetMapping("/searchByDocumentTypeCode")
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> retrieveByDocumentTypeCode(
    @RequestParam(name = "code") final String code) {
    final CatalogItemResponseDto responseDto = catalogItemService.findByCode(code);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> create(@Valid @RequestBody final CatalogItemRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final CatalogItemResponseDto responseDto = this.catalogItemService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final CatalogItemRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final CatalogItemResponseDto existing = catalogItemService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.catalogItemService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.catalogItemService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
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

**Ejemplo de como puedes crear y actualizar un recurso. (Recuerda manejar solo uno, ya que spring boot detecta si viene informado el id, entonces se trata de una actualización)

```java
@Service
@RequiredArgsConstructor
public class CatalogItemServiceImpl implements CatalogItemService {

  private final CatalogItemRepository catalogItemRepository;

  private final CatalogItemFieldsMapper catalogItemFieldsMapper;
  
  @Override
  public List<CatalogItemResponseDto> findAll() {
    final List<CatalogItemEntity> entities = catalogItemRepository.findAll().stream().toList();
    return catalogItemFieldsMapper.toDtoList(entities);
  }

  @Override
  public CatalogItemResponseDto findById(final Integer id) {
    final Optional<CatalogItemEntity> entityOptional = this.catalogItemRepository.findById(id);
    return entityOptional.map(this.catalogItemFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public CatalogItemResponseDto findByCode(final String code) {
    final Optional<CatalogItemEntity> entityOptional = this.catalogItemRepository.findByCodeAndIsActiveTrue(code);
    return entityOptional.map(this.catalogItemFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public CatalogItemResponseDto create(final CatalogItemRequestDto requestDto) {
    final CatalogItemEntity entity = this.catalogItemRepository.save(this.catalogItemFieldsMapper.toEntity(requestDto));
    return this.catalogItemFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(Integer id) {
    final CatalogItemEntity existingEntity = this.catalogItemRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.catalogItemRepository.deleteById(existingEntity.getId());
  }
```
