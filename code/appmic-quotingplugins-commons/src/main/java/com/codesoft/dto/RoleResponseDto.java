package com.codesoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {

  private Integer id;

  private String roleName;

  private String description;

  private Boolean isActive;
}
