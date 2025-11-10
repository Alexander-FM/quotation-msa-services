package com.codesoft.role.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDto {

  private Integer id;

  @NotNull(message = "El nombre del rol no puede ser nulo.")
  @NotEmpty(message = "El nombre del rol no puede estar vacío.")
  private String roleName;

  private String description;

  @NotNull(message = "El estado activo no puede ser nulo.")
  private Boolean isActive;
}
