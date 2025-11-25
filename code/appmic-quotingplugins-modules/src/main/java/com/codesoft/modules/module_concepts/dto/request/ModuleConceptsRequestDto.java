package com.codesoft.modules.module_concepts.dto.request;

import java.math.BigDecimal;

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
public class ModuleConceptsRequestDto {

  private Integer id;

  @NotNull(message = "The overheads cost must not be null.")
  private Short overheadsCost;

  private Short fee;

  private Short rebate;

  private BigDecimal profitMargin;

  @NotNull(message = "The module name must not be null.")
  @NotEmpty(message = "The module name must not be empty.")
  @Size(max = 100, message = "The module name must not exceed 100 characters.")
  private String moduleName;
}
