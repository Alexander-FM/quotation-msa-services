package com.codesoft.materials.material.client.adjustment_factor.service;

import com.codesoft.materials.material.client.adjustment_factor.dto.AdjustmentFactorResponseDto;

public interface AdjustmentFactorClient {

  AdjustmentFactorResponseDto searchByName(final String name);
}
