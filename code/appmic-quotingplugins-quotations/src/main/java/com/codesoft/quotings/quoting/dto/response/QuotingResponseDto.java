package com.codesoft.quotings.quoting.dto.response;

import java.math.BigDecimal;

import com.codesoft.quotings.modules.module.dto.response.ModuleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotingResponseDto {

  private Integer id;

  private ModuleResponseDto module;

  private Integer materialId;

  private BigDecimal quantity;

  private String descriptionRef;

  private Integer defaultYield;

  private BigDecimal defaultWidth;

  private BigDecimal defaultLength;
}
