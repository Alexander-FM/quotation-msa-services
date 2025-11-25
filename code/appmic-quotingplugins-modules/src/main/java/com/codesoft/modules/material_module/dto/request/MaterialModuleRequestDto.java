package com.codesoft.modules.material_module.dto.request;

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
public class MaterialModuleRequestDto {

  private Integer id;

  @NotNull(message = "The quantity must not be null.")
  private Short quantity;

  private Short pieces;

  @NotNull(message = "The module name must not be null.")
  @NotEmpty(message = "The module name must not be empty.")
  @Size(max = 100, message = "The module name must not exceed 100 characters.")
  private String moduleName;

  @Size(max = 50, message = "The material name must not exceed 50 characters.")
  private String materialName;
}
