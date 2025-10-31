package com.codesoft.catalogs.adjustment_factor.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentFactorResponseDto {

  private Integer id;

  private String name;

  private BigDecimal value;

  private Boolean isActive;
}
