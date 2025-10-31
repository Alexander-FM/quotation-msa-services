package com.codesoft.catalogs.adjustment_factor.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.mapper.AdjustmentFactorFieldsMapper;
import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import com.codesoft.catalogs.adjustment_factor.repository.AdjustmentFactorRepository;
import com.codesoft.catalogs.adjustment_factor.utils.AdjustmentFactorConstants;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdjustmentFactorServiceImpl implements AdjustmentFactorService {

  private final AdjustmentFactorRepository adjustmentFactorRepository;

  private final AdjustmentFactorFieldsMapper adjustmentFactorFieldsMapper;

  @Override
  public List<AdjustmentFactorResponseDto> findAll() {
    final List<AdjustmentFactorEntity> entities = adjustmentFactorRepository.findAll().stream().toList();
    return adjustmentFactorFieldsMapper.toDtoList(entities);
  }

  @Override
  public AdjustmentFactorResponseDto findById(final Integer id) {
    final Optional<AdjustmentFactorEntity> adjustmentFactorEntity = this.adjustmentFactorRepository.findById(id);
    return adjustmentFactorEntity.map(this.adjustmentFactorFieldsMapper::toDto).orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

}
