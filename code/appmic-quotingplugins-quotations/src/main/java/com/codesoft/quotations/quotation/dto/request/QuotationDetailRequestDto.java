package com.codesoft.quotations.quotation.dto.request;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.codesoft.quotations.modules.module.dto.request.ModuleRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuotationDetailRequestDto {

  private Integer id;

  @NotNull(message = "El módulo es obligatorio.")
  private ModuleRequestDto module;

  @NotNull(message = "La cantidad es obligatoria.")
  @Min(value = 1, message = "La cantidad mínima es 1.")
  private Integer quantity;

  @DecimalMin(value = "0.00", message = "El costo de transporte no puede ser negativo.")
  private BigDecimal transportationCost;

  @DecimalMin(value = "0.00", message = "La mano de obra no puede ser negativa.")
  private BigDecimal laborCost;

  @DecimalMin(value = "0.00", message = "El empaque no puede ser negativo.")
  private BigDecimal packingCost;

  @Valid
  @NotEmpty(message = "El detalle de la cotización debe tener materiales asociados.")
  private Set<QuotationDetailSubItemRequestDto> subItems = new HashSet<>();
}
