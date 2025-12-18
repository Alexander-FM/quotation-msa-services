package com.codesoft.quotings.modules.module_material.controller;

import java.util.List;

import com.codesoft.exception.BaseException;
import com.codesoft.quotings.modules.module_material.dto.request.ModuleMaterialRequestDto;
import com.codesoft.quotings.modules.module_material.dto.response.ModuleMaterialResponseDto;
import com.codesoft.quotings.modules.module_material.service.ModuleMaterialService;
import com.codesoft.quotings.modules.module_material.utils.ModuleMaterialConstants;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("${app.endpoints.module_material}")
@RequiredArgsConstructor
public class ModuleMaterialController {

  private final ModuleMaterialService moduleMaterialService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<ModuleMaterialResponseDto>>> retrieve() {
    final List<ModuleMaterialResponseDto> responseDtoList = moduleMaterialService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(ModuleMaterialConstants.FOUND_MESSAGE, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<ModuleMaterialResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final ModuleMaterialResponseDto responseDto = moduleMaterialService.findById(id);
    log.info("Module material was found, this is la data: {}", responseDto);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(ModuleMaterialConstants.FOUND_MESSAGE, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<ModuleMaterialResponseDto>> create(@Valid @RequestBody final ModuleMaterialRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final ModuleMaterialResponseDto responseDto = this.moduleMaterialService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(GenericResponseUtils.buildGenericResponseSuccess(ModuleMaterialConstants.SAVED_MESSAGE, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<ModuleMaterialResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final ModuleMaterialRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final ModuleMaterialResponseDto existing = moduleMaterialService.findById(id);
    requestDto.setId(existing.getId());
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(ModuleMaterialConstants.UPDATED_MESSAGE,
        this.moduleMaterialService.create(requestDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.moduleMaterialService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(ModuleMaterialConstants.REMOVED_MESSAGE, null));
  }
}
