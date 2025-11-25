package com.codesoft.modules.material_module.controller;

import java.util.List;

import com.codesoft.exception.BaseException;
import com.codesoft.modules.material_module.dto.request.MaterialModuleRequestDto;
import com.codesoft.modules.material_module.dto.response.MaterialModuleResponseDto;
import com.codesoft.modules.material_module.service.MaterialModuleService;
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
@RequestMapping("/api/modules/material-module")
@RequiredArgsConstructor
public class MaterialModuleController {

  private final MaterialModuleService materialModuleService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<MaterialModuleResponseDto>>> retrieve() {
    final List<MaterialModuleResponseDto> responseDtoList = materialModuleService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<MaterialModuleResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final MaterialModuleResponseDto responseDto = materialModuleService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @GetMapping("/searchByModuleName")
  public ResponseEntity<GenericResponse<List<MaterialModuleResponseDto>>> retrieveByModuleName(
      @RequestParam(name = "moduleName") final String moduleName) {
    final List<MaterialModuleResponseDto> responseDtoList = materialModuleService.findByModuleName(moduleName);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDtoList));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<MaterialModuleResponseDto>> create(@Valid @RequestBody final MaterialModuleRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final MaterialModuleResponseDto responseDto = this.materialModuleService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<MaterialModuleResponseDto>> update(@PathVariable(value = "id") final Integer id,
      @Valid @RequestBody final MaterialModuleRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final MaterialModuleResponseDto existing = materialModuleService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
          .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.materialModuleService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.materialModuleService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
