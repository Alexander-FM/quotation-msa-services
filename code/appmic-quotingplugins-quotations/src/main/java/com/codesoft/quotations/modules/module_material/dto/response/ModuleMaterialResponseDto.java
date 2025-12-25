package com.codesoft.quotations.modules.module_material.dto.response;

import java.math.BigDecimal;

import com.codesoft.quotations.modules.module.dto.response.ModuleResponseDto;
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
