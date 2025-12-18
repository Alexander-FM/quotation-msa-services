package com.codesoft.quotings.module.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleMaterialResponseDto {

  private Integer id;

  private ModuleResponseDto module;

  private Integer materialId;

  private BigDecimal quantity;

  private String descriptionRef;

  private Integer defaultYield;

  private BigDecimal defaultWidth;

  private BigDecimal defaultLength;
}
