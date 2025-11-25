package com.codesoft.materials.material.controller;

import java.util.List;

import com.codesoft.exception.BaseException;
import com.codesoft.materials.material.dto.request.MaterialRequestDto;
import com.codesoft.materials.material.dto.response.MaterialResponseDto;
import com.codesoft.materials.material.service.MaterialService;
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
@RequestMapping("/api/materials/material")
@RequiredArgsConstructor
public class MaterialController {

  private final MaterialService materialService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<MaterialResponseDto>>> retrieve() {
    final List<MaterialResponseDto> responseDtoList = materialService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<MaterialResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final MaterialResponseDto responseDto = materialService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<MaterialResponseDto>> create(@Valid @RequestBody final MaterialRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final MaterialResponseDto responseDto = this.materialService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<MaterialResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final MaterialRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final MaterialResponseDto existing = materialService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.materialService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.materialService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
