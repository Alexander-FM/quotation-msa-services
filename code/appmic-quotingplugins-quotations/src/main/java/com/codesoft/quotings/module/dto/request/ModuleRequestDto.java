package com.codesoft.quotings.module.dto.request;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
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
public class ModuleRequestDto {

  private Integer id;

  @NotNull(message = "El nombre del módulo no debe ser nulo.")
  @NotEmpty(message = "El nombre del módulo no debe estar vacío.")
  @Size(max = 100, message = "El nombre del módulo no debe exceder 100 caracteres.")
  private String name;

  @Size(max = 255, message = "La descripción no debe exceder 255 caracteres.")
  private String description;

  @Size(max = 100, message = "Las dimensiones no deben exceder 100 caracteres.")
  private String dimensions;

  private Boolean isActive;

  @Digits(integer = 3, fraction = 2, message = "overheadsCostPercentage formato inválido.")
  @DecimalMin(value = "0.00")
  @DecimalMax(value = "100.00")
  private BigDecimal overheadsCostPercentage;

  @Digits(integer = 3, fraction = 2)
  @DecimalMin(value = "0.00")
  @DecimalMax(value = "100.00")
  private BigDecimal feePercentage;

  @Digits(integer = 3, fraction = 2)
  @DecimalMin(value = "0.00")
  @DecimalMax(value = "100.00")
  private BigDecimal rebatePercentage;

  @Digits(integer = 3, fraction = 2)
  @DecimalMin(value = "0.00")
  @DecimalMax(value = "100.00")
  private BigDecimal profitMarginPercentage;

  private Set<ModuleMaterialRequestDto> materials = new HashSet<>();
}

