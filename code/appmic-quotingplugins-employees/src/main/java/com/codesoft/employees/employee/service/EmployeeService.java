package com.codesoft.employees.employee.service;

import java.util.List;
import java.util.Set;

import com.codesoft.employees.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employees.employee.dto.response.EmployeeResponseDto;

public interface EmployeeService {

  List<EmployeeResponseDto> findAll();

  List<EmployeeResponseDto> searchAllByDocumentNumber(final Set<String> documentNumberList);

  EmployeeResponseDto findById(final Integer id);

  EmployeeResponseDto searchByDocumentNumber(final String documentNumber);

  EmployeeResponseDto create(final EmployeeRequestDto requestDto);

  void deleteById(final Integer id);

}
