package com.codesoft.employees.user.controller;

import java.util.List;

import com.codesoft.employees.user.dto.request.UserRequestDto;
import com.codesoft.employees.user.dto.response.UserResponseDto;
import com.codesoft.employees.user.service.UserService;
import com.codesoft.employees.user.utils.UserConstants;
import com.codesoft.employees.user.utils.UserUtils;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.ValidateInputObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final BCryptPasswordEncoder passwordEncoder;

  @GetMapping
  public ResponseEntity<GenericResponse<List<UserResponseDto>>> retrieve() {
    final List<UserResponseDto> users = this.userService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, users));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final UserResponseDto users = this.userService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(UserConstants.FOUND_MESSAGE, users));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<UserResponseDto>> create(@RequestBody final UserRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    ValidateInputObject.validRequestDto(requestDto);
    UserUtils.encodePassword(requestDto, passwordEncoder);
    final UserResponseDto responseDto = this.userService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(GenericResponseUtils.buildGenericResponseSuccess(UserConstants.SAVED_MESSAGE, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<UserResponseDto>> update(@PathVariable(value = "id") final Integer id,
      @RequestBody final UserRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final UserResponseDto existing = this.userService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      ValidateInputObject.validRequestDto(requestDto);
      requestDto.setId(existing.getId());
      UserUtils.encodePassword(requestDto, passwordEncoder);
      return ResponseEntity.status(HttpStatus.OK)
          .body(GenericResponseUtils.buildGenericResponseSuccess(UserConstants.UPDATED_MESSAGE, this.userService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(GenericResponseUtils.buildGenericResponseError(UserConstants.FIND_ERROR_MESSAGE, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.userService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(GenericResponseUtils.buildGenericResponseSuccess(UserConstants.REMOVED_MESSAGE, null));
  }

  @GetMapping("/login")
  public ResponseEntity<GenericResponse<UserResponseDto>> loginByUsername(@RequestParam(name = "username") final String username,
      @RequestParam(name = "password") final String password) {
    final UserResponseDto response = this.userService.findByUsername(username, password);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(UserConstants.FOUND_MESSAGE, response));
  }
}
