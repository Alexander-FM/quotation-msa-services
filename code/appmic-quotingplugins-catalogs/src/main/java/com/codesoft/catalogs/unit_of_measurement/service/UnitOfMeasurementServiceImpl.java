package com.codesoft.catalogs.unit_of_measurement.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.catalogs.unit_of_measurement.dto.request.UnitOfMeasurementRequestDto;
import com.codesoft.catalogs.unit_of_measurement.dto.response.UnitOfMeasurementResponseDto;
import com.codesoft.catalogs.unit_of_measurement.exception.UnitOfMeasurementException;
import com.codesoft.catalogs.unit_of_measurement.exception.UnitOfMeasurementMessage;
import com.codesoft.catalogs.unit_of_measurement.mapper.UnitOfMeasurementFieldsMapper;
import com.codesoft.catalogs.unit_of_measurement.model.entity.UnitOfMeasurementEntity;
import com.codesoft.catalogs.unit_of_measurement.repository.UnitOfMeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {

  private final UnitOfMeasurementRepository unitOfMeasurementRepository;

  private final UnitOfMeasurementFieldsMapper unitOfMeasurementFieldsMapper;

  @Override
  public List<com.codesoft.catalogs.unit_of_measurement.dto.response.UnitOfMeasurementResponseDto> findAll() {
    final List<UnitOfMeasurementEntity> entities = unitOfMeasurementRepository.findAll().stream().toList();
    return unitOfMeasurementFieldsMapper.toDtoList(entities);
  }

  @Override
  public UnitOfMeasurementResponseDto findById(final Integer id) {
    final Optional<UnitOfMeasurementEntity> entityOptional = this.unitOfMeasurementRepository.findById(id);
    return entityOptional.map(this.unitOfMeasurementFieldsMapper::toDto)
      .orElseThrow(() -> new UnitOfMeasurementException(UnitOfMeasurementMessage.CATALOG_ITEM_NOT_FOUND));
  }

  @Override
  public UnitOfMeasurementResponseDto findByName(final String name) {
    final Optional<UnitOfMeasurementEntity> entityOptional =
      this.unitOfMeasurementRepository.findByNameContainingIgnoreCase(name);
    return entityOptional.map(this.unitOfMeasurementFieldsMapper::toDto)
      .orElseThrow(() -> new UnitOfMeasurementException(UnitOfMeasurementMessage.CATALOG_ITEM_NOT_FOUND));
  }

  @Override
  public UnitOfMeasurementResponseDto create(final UnitOfMeasurementRequestDto requestDto) {
    try {
      final UnitOfMeasurementEntity entity = this.unitOfMeasurementRepository.save(this.unitOfMeasurementFieldsMapper.toEntity(requestDto));
      return this.unitOfMeasurementFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException ex) {
      throw new UnitOfMeasurementException(UnitOfMeasurementMessage.CATALOG_ITEM_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    final UnitOfMeasurementEntity existingEntity = this.unitOfMeasurementRepository.findById(id)
      .orElseThrow(() -> new UnitOfMeasurementException(UnitOfMeasurementMessage.CATALOG_ITEM_NOT_FOUND));
    this.unitOfMeasurementRepository.deleteById(existingEntity.getId());
  }
}
