package com.codesoft.modules.module_concepts.controller;

import java.util.List;

import com.codesoft.exception.BaseException;
import com.codesoft.modules.module_concepts.dto.request.ModuleConceptsRequestDto;
import com.codesoft.modules.module_concepts.dto.response.ModuleConceptsResponseDto;
import com.codesoft.modules.module_concepts.service.ModuleConceptsService;
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
@RequestMapping("/api/modules/module-concepts")
@RequiredArgsConstructor
public class ModuleConceptsController {

  private final ModuleConceptsService moduleConceptsService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<ModuleConceptsResponseDto>>> retrieve() {
    final List<ModuleConceptsResponseDto> responseDtoList = moduleConceptsService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<ModuleConceptsResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final ModuleConceptsResponseDto responseDto = moduleConceptsService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @GetMapping("/searchByModuleName")
  public ResponseEntity<GenericResponse<ModuleConceptsResponseDto>> retrieveByModuleName(
      @RequestParam(name = "moduleName") final String moduleName) {
    final ModuleConceptsResponseDto responseDto = moduleConceptsService.findByModuleName(moduleName);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<ModuleConceptsResponseDto>> create(@Valid @RequestBody final ModuleConceptsRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final ModuleConceptsResponseDto responseDto = this.moduleConceptsService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<ModuleConceptsResponseDto>> update(@PathVariable(value = "id") final Integer id,
      @Valid @RequestBody final ModuleConceptsRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final ModuleConceptsResponseDto existing = moduleConceptsService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
          .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.moduleConceptsService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.moduleConceptsService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
