package com.codesoft.employees.role.service;

import java.util.List;

import com.codesoft.employees.role.dto.request.RoleRequestDto;
import com.codesoft.employees.role.dto.response.RoleResponseDto;

public interface RoleService {

  List<RoleResponseDto> findAll();

  RoleResponseDto findById(final Integer id);

  RoleResponseDto create(final RoleRequestDto requestDto);

  void deleteById(final Integer id);

}
