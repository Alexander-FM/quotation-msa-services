package com.codesoft.quotations.client.material.dto;

import java.math.BigDecimal;

import com.codesoft.quotations.client.material.utils.MaterialCalculationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialResponseDto {

  private Integer id;

  private String name;

  private String description;

  private BigDecimal unitCost;

  private String unidadOfMeasurementName;

  private MaterialCalculationTypeEnum calculationType;

  private String adjustmentFactorName;

  private BigDecimal adjustmentFactorValue;

  private Integer thicknessMicrons;

  private Boolean isActive;
}
