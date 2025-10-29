package com.codesoft.catalogs.adjustment_factor.service;

import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;

public interface AdjustmentFactorService {

  List<AdjustmentFactorResponseDto> findAll();

}
