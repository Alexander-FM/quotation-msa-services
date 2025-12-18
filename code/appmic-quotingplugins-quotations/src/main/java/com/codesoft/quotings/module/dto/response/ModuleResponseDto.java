package com.codesoft.quotings.module.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleResponseDto {

  private Integer id;

  private String name;

  private String description;

  private String dimensions;

  private Boolean isActive;

  private BigDecimal overheadsCostPercentage;

  private BigDecimal feePercentage;

  private BigDecimal rebatePercentage;

  private BigDecimal profitMarginPercentage;

  private LocalDateTime createdAt;

  private Set<ModuleMaterialResponseDto> materials;
}

