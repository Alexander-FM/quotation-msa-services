package com.codesoft.materials.material.client.adjustment_factor.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AdjustmentFactorResponseDto {

  private Integer id;

  private String name;

  private BigDecimal value;

  private Boolean isActive;
}
