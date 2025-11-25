package com.codesoft.modules.module_concepts.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleConceptsResponseDto {

  private Integer id;

  private Short overheadsCost;

  private Short fee;

  private Short rebate;

  private BigDecimal profitMargin;

  private String moduleName;
}
