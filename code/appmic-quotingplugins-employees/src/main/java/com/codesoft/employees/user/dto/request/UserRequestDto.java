package com.codesoft.employees.user.dto.request;

import java.util.HashSet;
import java.util.Set;

import com.codesoft.employees.role.dto.request.RoleRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

  private Integer id;

  @NotNull(message = "El username no puede ser nulo.")
  @NotEmpty(message = "El username no puede estar vacío.")
  private String username;

  @NotNull(message = "El password no puede ser nulo.")
  @NotEmpty(message = "El password no puede estar vacío.")
  private String password;

  @NotNull(message = "El estado activo del usuario no puede ser nulo.")
  private Boolean isActive = true;

  @NotEmpty(message = "Los roles no pueden estar vacío.")
  private Set<RoleRequestDto> roles = new HashSet<>();
}
