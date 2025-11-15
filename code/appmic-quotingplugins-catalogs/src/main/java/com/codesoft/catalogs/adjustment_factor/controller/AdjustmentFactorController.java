package com.codesoft.catalogs.adjustment_factor.controller;

import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogs/adjustment-factor")
@RequiredArgsConstructor
public class AdjustmentFactorController {

  private final AdjustmentFactorService adjustmentFactorService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<AdjustmentFactorResponseDto>>> retrieve() {
    final List<AdjustmentFactorResponseDto> adjustmentFactors = adjustmentFactorService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, adjustmentFactors));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final AdjustmentFactorResponseDto adjustmentFactors = adjustmentFactorService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, adjustmentFactors));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> create(@RequestBody final AdjustmentFactorRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    ValidateInputObject.validRequestDto(requestDto);
    final AdjustmentFactorResponseDto responseDto = this.adjustmentFactorService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> update(@PathVariable(value = "id") final Integer id,
      @RequestBody final AdjustmentFactorRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final AdjustmentFactorResponseDto existing = adjustmentFactorService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      ValidateInputObject.validRequestDto(requestDto);
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
          .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.adjustmentFactorService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.adjustmentFactorService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
