package com.codesoft.employees.user.service;

import java.util.List;

import com.codesoft.employees.user.dto.request.UserRequestDto;
import com.codesoft.employees.user.dto.response.UserResponseDto;

public interface UserService {

  List<UserResponseDto> findAll();

  UserResponseDto findById(final Integer id);

  UserResponseDto create(final UserRequestDto requestDto);

  void deleteById(final Integer id);

  UserResponseDto findByUsername(final String username, final String password);
}
