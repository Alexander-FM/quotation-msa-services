package com.codesoft.employee.service;

import java.util.List;

import com.codesoft.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employee.dto.response.EmployeeResponseDto;

public interface EmployeeService {

  List<EmployeeResponseDto> findAll();

  EmployeeResponseDto findById(final Integer id);

  EmployeeResponseDto create(final EmployeeRequestDto requestDto);

  void deleteById(final Integer id);

}
