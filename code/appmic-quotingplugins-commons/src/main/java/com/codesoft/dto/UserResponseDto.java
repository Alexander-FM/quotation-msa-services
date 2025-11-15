package com.codesoft.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Integer id;

  private String username;

  @JsonIgnore                       // evita que Jackson lo serialice en la respuesta
  @ToString.Exclude                 // lo excluye de toString()
  @EqualsAndHashCode.Exclude       // lo excluye de equals/hashCode
  @Getter(AccessLevel.NONE)         // no generar getter
  @Setter(AccessLevel.NONE)         // no generar setter
  private String password;

  private Boolean isActive;

  private Set<RoleResponseDto> roles = new HashSet<>();
}
