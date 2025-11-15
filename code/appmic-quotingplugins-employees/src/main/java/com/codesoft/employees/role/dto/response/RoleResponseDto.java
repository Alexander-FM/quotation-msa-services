package com.codesoft.employees.role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDto {

  private Integer id;

  private String roleName;

  private String description;

  private Boolean isActive;
}
