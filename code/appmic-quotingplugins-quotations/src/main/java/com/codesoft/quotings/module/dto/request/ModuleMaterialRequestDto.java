package com.codesoft.quotings.module.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleMaterialRequestDto {

  private Integer id;

  @NotNull(message = "El moduleId es obligatorio.")
  private ModuleRequestDto moduleId;

  @NotNull(message = "El materialId es obligatorio.")
  private Integer materialId;

  @NotNull(message = "La cantidad es obligatoria.")
  @Digits(integer = 8, fraction = 2, message = "quantity formato inválido.")
  @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0.")
  private BigDecimal quantity;

  @Size(max = 150, message = "La descripción de referencia no debe exceder 150 caracteres.")
  private String descriptionRef;

  private Integer defaultYield;

  @Digits(integer = 8, fraction = 2)
  private BigDecimal defaultWidth;

  @Digits(integer = 8, fraction = 2)
  private BigDecimal defaultLength;
}

