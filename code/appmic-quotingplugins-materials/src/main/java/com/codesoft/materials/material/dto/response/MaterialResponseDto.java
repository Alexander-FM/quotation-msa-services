package com.codesoft.materials.material.dto.response;

import java.math.BigDecimal;

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

  private BigDecimal width;

  private BigDecimal length;

  private BigDecimal height;

  private Boolean usedFormula;

  private Boolean isActive;

  private String unidadName;

  private String adjustmentFactorName;
}
