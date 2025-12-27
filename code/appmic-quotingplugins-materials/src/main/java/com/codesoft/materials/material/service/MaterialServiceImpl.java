package com.codesoft.materials.material.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.codesoft.materials.material.client.adjustment_factor.dto.AdjustmentFactorResponseDto;
import com.codesoft.materials.material.client.adjustment_factor.service.AdjustmentFactorClient;
import com.codesoft.materials.material.client.unit_of_measurement.dto.UnitOfMeasurementResponseDto;
import com.codesoft.materials.material.client.unit_of_measurement.service.UnitOfMeasurementClient;
import com.codesoft.materials.material.dto.request.MaterialRequestDto;
import com.codesoft.materials.material.dto.response.MaterialResponseDto;
import com.codesoft.materials.material.exception.MaterialException;
import com.codesoft.materials.material.exception.MaterialMessage;
import com.codesoft.materials.material.mapper.MaterialFieldsMapper;
import com.codesoft.materials.material.model.entity.MaterialEntity;
import com.codesoft.materials.material.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialServiceImpl implements MaterialService {

  private final MaterialRepository materialRepository;

  private final MaterialFieldsMapper materialFieldsMapper;

  private final AdjustmentFactorClient adjustmentFactorClient;

  private final UnitOfMeasurementClient unitOfMeasurementClient;

  @Override
  public List<MaterialResponseDto> findAll() {
    final List<MaterialEntity> entities = materialRepository.findAll();
    return materialFieldsMapper.toDtoList(entities);
  }

  @Override
  public List<MaterialResponseDto> findAllById(final Set<Integer> idList) {
    final List<MaterialEntity> entities = materialRepository.findAllById(idList);
    return materialFieldsMapper.toDtoList(entities);
  }

  @Override
  public MaterialResponseDto findById(final Integer id) {
    final Optional<MaterialEntity> entityOptional = this.materialRepository.findById(id);
    return entityOptional.map(this.materialFieldsMapper::toDto)
      .orElseThrow(() -> new MaterialException(MaterialMessage.MATERIAL_NOT_FOUND));
  }

  @Override
  public MaterialResponseDto create(final MaterialRequestDto requestDto) {
    try {
      if (requestDto.getAdjustmentFactorName() != null) {
        final AdjustmentFactorResponseDto adjustmentFactor = this.adjustmentFactorClient.searchByName(requestDto.getAdjustmentFactorName());
        requestDto.setAdjustmentFactorName(adjustmentFactor.getName());
        requestDto.setAdjustmentFactorValue(adjustmentFactor.getValue());
      }
      final UnitOfMeasurementResponseDto unitOfMeasurement = unitOfMeasurementClient.searchByName(requestDto.getUnidadOfMeasurementName());
      requestDto.setUnidadOfMeasurementName(unitOfMeasurement.getName());
      final MaterialEntity entity = this.materialRepository.save(this.materialFieldsMapper.toEntity(requestDto));
      return this.materialFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException e) {
      log.warn("Data integrity violation when creating material: {}", e.getMessage());
      throw new MaterialException(MaterialMessage.MATERIAL_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.materialRepository.findById(id)
      .orElseThrow(() -> new MaterialException(MaterialMessage.MATERIAL_NOT_FOUND));
    this.materialRepository.deleteById(id);
  }
}
