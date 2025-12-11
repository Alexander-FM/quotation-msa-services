package com.codesoft.materials.material.dto.request;

import java.math.BigDecimal;

import com.codesoft.materials.material.utils.MaterialCalculationTypeEnum;
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
  @DecimalMax(value = "99999999.99", message = "The unit cost must be less than or equal to 99,999,999.99")
  @Digits(integer = 8, fraction = 2,
    message = "The unit cost must not exceed 99,999,999.99 (maximum 8 digits before and 2 digits after the decimal point).")
  private BigDecimal unitCost;

  private String unidadOfMeasurementName;

  private MaterialCalculationTypeEnum calculationType;

  private String adjustmentFactorName;

  private BigDecimal adjustmentFactorValue;

  private Integer thicknessMicrons;

  private Boolean isActive;
}
