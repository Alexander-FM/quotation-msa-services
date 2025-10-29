package com.codesoft.catalogs.adjustment_factor.controller;

import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.service.AdjustmentFactorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adjustment-factors")
@RequiredArgsConstructor
public class AdjustmentFactorController {

  private final AdjustmentFactorService adjustmentFactorService;

  @GetMapping
  public ResponseEntity<List<AdjustmentFactorResponseDto>> getAllAdjustmentFactors() {
    List<AdjustmentFactorResponseDto> adjustmentFactors = adjustmentFactorService.findAll();
    return ResponseEntity.ok(adjustmentFactors);
  }

}
