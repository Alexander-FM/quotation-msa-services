package com.codesoft.employees.employee.controller;

import java.util.List;

import com.codesoft.employees.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employees.employee.dto.response.EmployeeResponseDto;
import com.codesoft.employees.employee.service.EmployeeService;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees/employee")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<EmployeeResponseDto>>> retrieve() {
    final List<EmployeeResponseDto> employees = employeeService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, employees));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<EmployeeResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final EmployeeResponseDto employees = employeeService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, employees));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<EmployeeResponseDto>> create(@Valid @RequestBody final EmployeeRequestDto requestDto) {
    final EmployeeResponseDto responseDto = this.employeeService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<EmployeeResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final EmployeeRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final EmployeeResponseDto existing = employeeService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.employeeService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.employeeService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
