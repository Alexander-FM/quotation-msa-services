package com.codesoft.service;

import com.codesoft.dto.UserResponseDto;

public interface UserService {

  /**
   * Valida el usuario especificado y devuelve la información necesaria para la autenticación.
   *
   * @param username nombre de usuario a validar; no debe ser {@code null} ni vacío.
   * @param password contraseña del usuario a validar; no debe ser {@code null} ni vacío.
   * @return {@link UserResponseDto} con los datos del usuario si existe, o {@code null} si no se encuentra
   */
  UserResponseDto validateUser(final String username, final String password);
}
