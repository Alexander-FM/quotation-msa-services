package com.codesoft.modules.module.dto.request;

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
public class ModuleRequestDto {

  private Integer id;

  @NotNull(message = "The module name must not be null.")
  @NotEmpty(message = "The module name must not be empty.")
  @Size(max = 100, message = "The module name must not exceed 100 characters.")
  private String name;

  @Size(max = 500, message = "The dimensions must not exceed 500 characters.")
  private String dimensions;

  private Boolean isActive;
}
