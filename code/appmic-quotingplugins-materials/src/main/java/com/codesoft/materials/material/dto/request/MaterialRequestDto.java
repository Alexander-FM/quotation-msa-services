package com.codesoft.materials.material.dto.request;

import java.math.BigDecimal;

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
public class MaterialRequestDto {

  private Integer id;

  @NotNull(message = "The material name must not be null.")
  @NotEmpty(message = "The material name must not be empty.")
  @Size(max = 50, message = "The material name must not exceed 50 characters.")
  private String name;

  @Size(max = 255, message = "The material description must not exceed 255 characters.")
  private String description;

  @NotNull(message = "The unit cost must not be null.")
  @DecimalMin(value = "0.0", inclusive = false, message = "The unit cost must be greater than zero.")
  @DecimalMax(value = "99999999.99", message = "The unit cost must be less than or equal to 99,999,999.99.")
  @Digits(integer = 8, fraction = 2,
    message = "The unit cost must not exceed 99,999,999.99 (maximum 8 digits before and 2 digits after the decimal point).")
  private BigDecimal unitCost;

  @DecimalMin(value = "0.0", inclusive = true, message = "The width must be greater than or equal to zero.")
  @DecimalMax(value = "99999999.99", message = "The width must be less than or equal to 99,999,999.99.")
  @Digits(integer = 8, fraction = 2,
    message = "The width must not exceed 99,999,999.99 (maximum 8 digits before and 2 digits after the decimal point).")
  private BigDecimal width;

  @DecimalMin(value = "0.0", inclusive = true, message = "The length must be greater than or equal to zero.")
  @DecimalMax(value = "99999999.99", message = "The length must be less than or equal to 99,999,999.99.")
  @Digits(integer = 8, fraction = 2,
    message = "The length must not exceed 99,999,999.99 (maximum 8 digits before and 2 digits after the decimal point).")
  private BigDecimal length;

  @DecimalMin(value = "0.0", inclusive = true, message = "The height must be greater than or equal to zero.")
  @DecimalMax(value = "99999999.99", message = "The height must be less than or equal to 99,999,999.99.")
  @Digits(integer = 8, fraction = 2,
    message = "The height must not exceed 99,999,999.99 (maximum 8 digits before and 2 digits after the decimal point).")
  private BigDecimal height;

  private Boolean usedFormula;

  private Boolean isActive;

  @NotNull(message = "The unit name must not be null.")
  @NotEmpty(message = "The unit name must not be empty.")
  @Size(max = 20, message = "The unit name must not exceed 20 characters.")
  private String unidadName;

  @Size(max = 40, message = "The adjustment factor name must not exceed 40 characters.")
  private String adjustmentFactorName;
}
