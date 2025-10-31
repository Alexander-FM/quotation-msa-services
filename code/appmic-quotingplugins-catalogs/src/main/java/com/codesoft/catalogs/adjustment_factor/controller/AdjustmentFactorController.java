package com.codesoft.catalogs.adjustment_factor.controller;

import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adjustment-factors")
@RequiredArgsConstructor
public class AdjustmentFactorController {

  private final AdjustmentFactorService adjustmentFactorService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<AdjustmentFactorResponseDto>>> retrieveAllAdjustmentFactors() {
    final List<AdjustmentFactorResponseDto> adjustmentFactors = adjustmentFactorService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, adjustmentFactors));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<AdjustmentFactorResponseDto>> retrieveAdjustmentFactorById(
    @PathVariable(value = "id") final Integer id) {
    final AdjustmentFactorResponseDto adjustmentFactors = adjustmentFactorService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, adjustmentFactors));
  }

}
