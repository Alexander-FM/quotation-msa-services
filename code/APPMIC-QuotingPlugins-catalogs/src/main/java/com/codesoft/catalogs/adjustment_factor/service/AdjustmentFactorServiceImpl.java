package com.codesoft.catalogs.adjustment_factor.service;

import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.mapper.AdjustmentFactorFieldsMapper;
import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import com.codesoft.catalogs.adjustment_factor.repository.AdjustmentFactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdjustmentFactorServiceImpl implements AdjustmentFactorService {

  private final AdjustmentFactorRepository adjustmentFactorRepository;
  private final AdjustmentFactorFieldsMapper adjustmentFactorFieldsMapper;

  @Override
  public List<AdjustmentFactorResponseDto> findAll() {
    List<AdjustmentFactorEntity> entities = (List<AdjustmentFactorEntity>) adjustmentFactorRepository.findAll();
    return adjustmentFactorFieldsMapper.toDtoList(entities);
  }

}
