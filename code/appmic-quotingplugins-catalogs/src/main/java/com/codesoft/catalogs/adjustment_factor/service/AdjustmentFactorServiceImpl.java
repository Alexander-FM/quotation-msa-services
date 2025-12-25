package com.codesoft.catalogs.adjustment_factor.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.exception.AdjustmentFactorException;
import com.codesoft.catalogs.adjustment_factor.exception.AdjustmentFactorMessageEnum;
import com.codesoft.catalogs.adjustment_factor.mapper.AdjustmentFactorFieldsMapper;
import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import com.codesoft.catalogs.adjustment_factor.repository.AdjustmentFactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
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
    return adjustmentFactorEntity.map(this.adjustmentFactorFieldsMapper::toDto)
      .orElseThrow(() -> new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND));
  }

  @Override
  public AdjustmentFactorResponseDto create(final AdjustmentFactorRequestDto requestDto) {
    try {
      final AdjustmentFactorEntity entity = this.adjustmentFactorRepository.save(this.adjustmentFactorFieldsMapper.toEntity(requestDto));
      return this.adjustmentFactorFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException e) {
      log.warn("Data integrity violation when creating adjustment factor: {}", e.getMessage());
      throw new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(Integer id) {
    final AdjustmentFactorEntity existingEntity = this.adjustmentFactorRepository.findById(id)
      .orElseThrow(() -> new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND));
    this.adjustmentFactorRepository.deleteById(existingEntity.getId());
  }

  @Override
  public AdjustmentFactorResponseDto findByName(final String name) {
    final Optional<AdjustmentFactorEntity> entityOptional =
      this.adjustmentFactorRepository.findByName(name);
    return entityOptional.map(this.adjustmentFactorFieldsMapper::toDto)
      .orElseThrow(() -> new AdjustmentFactorException(AdjustmentFactorMessageEnum.ADJUSTMENT_FACTOR_NOT_FOUND));
  }
}
