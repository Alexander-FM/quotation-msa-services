package com.codesoft.modules.material_module.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialModuleResponseDto {

  private Integer id;

  private Short quantity;

  private Short pieces;

  private String moduleName;

  private String materialName;
}
