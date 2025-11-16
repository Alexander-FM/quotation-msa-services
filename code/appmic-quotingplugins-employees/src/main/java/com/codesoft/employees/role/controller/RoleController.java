package com.codesoft.employees.role.controller;

import java.util.List;

import com.codesoft.employees.role.dto.request.RoleRequestDto;
import com.codesoft.employees.role.dto.response.RoleResponseDto;
import com.codesoft.employees.role.service.RoleService;
import com.codesoft.employees.role.utils.RoleConstants;
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
@RequestMapping("/api/employees/role")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<RoleResponseDto>>> retrieve() {
    final List<RoleResponseDto> roles = this.roleService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, roles));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<RoleResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final RoleResponseDto roles = this.roleService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(RoleConstants.FOUND_MESSAGE, roles));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<RoleResponseDto>> create(@Valid @RequestBody final RoleRequestDto requestDto) {
    final RoleResponseDto responseDto = this.roleService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(GenericResponseUtils.buildGenericResponseSuccess(RoleConstants.SAVED_MESSAGE, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<RoleResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final RoleRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final RoleResponseDto existing = this.roleService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(RoleConstants.UPDATED_MESSAGE, this.roleService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(GenericResponseUtils.buildGenericResponseError(RoleConstants.FIND_ERROR_MESSAGE, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.roleService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(RoleConstants.REMOVED_MESSAGE, null));
  }
}
