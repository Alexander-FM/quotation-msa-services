package com.codesoft.modules.module.controller;

import java.util.List;

import com.codesoft.exception.BaseException;
import com.codesoft.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.modules.module.dto.response.ModuleResponseDto;
import com.codesoft.modules.module.service.ModuleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modules/module")
@RequiredArgsConstructor
public class ModuleController {

  private final ModuleService moduleService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<ModuleResponseDto>>> retrieve() {
    final List<ModuleResponseDto> responseDtoList = moduleService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<ModuleResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final ModuleResponseDto responseDto = moduleService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @GetMapping("/searchByName")
  public ResponseEntity<GenericResponse<ModuleResponseDto>> retrieveByName(
      @RequestParam(name = "name") final String name) {
    final ModuleResponseDto responseDto = moduleService.findByName(name);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<ModuleResponseDto>> create(@Valid @RequestBody final ModuleRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final ModuleResponseDto responseDto = this.moduleService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<ModuleResponseDto>> update(@PathVariable(value = "id") final Integer id,
      @Valid @RequestBody final ModuleRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final ModuleResponseDto existing = moduleService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
          .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.moduleService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.moduleService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
