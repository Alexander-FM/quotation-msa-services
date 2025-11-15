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
