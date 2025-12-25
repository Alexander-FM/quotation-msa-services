package com.codesoft.quotations.quotation.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuotationDetailSubItemRequestDto {

  private Integer id;

  @NotNull(message = "El ID del material es obligatorio.")
  private Integer materialId;

  @NotNull(message = "La cantidad del material es obligatoria.")
  @DecimalMin(value = "0.0001", message = "La cantidad debe ser mayor a 0.")
  private BigDecimal quantity;

  @NotNull(message = "El costo de materia prima es obligatorio.")
  @DecimalMin(value = "0.00", message = "El costo MP no puede ser negativo.")
  private BigDecimal rawMaterialCost;

  @NotNull(message = "Las piezas son obligatorias.")
  private Integer pieces;
}
