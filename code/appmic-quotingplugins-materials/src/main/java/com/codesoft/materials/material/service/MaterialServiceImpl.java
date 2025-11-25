package com.codesoft.materials.material.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.exception.BaseException;
import com.codesoft.materials.material.dto.request.MaterialRequestDto;
import com.codesoft.materials.material.dto.response.MaterialResponseDto;
import com.codesoft.materials.material.mapper.MaterialFieldsMapper;
import com.codesoft.materials.material.model.entity.MaterialEntity;
import com.codesoft.materials.material.repository.MaterialRepository;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

  private final MaterialRepository materialRepository;

  private final MaterialFieldsMapper materialFieldsMapper;

  @Override
  public List<MaterialResponseDto> findAll() {
    final List<MaterialEntity> entities = materialRepository.findAll().stream().toList();
    return materialFieldsMapper.toDtoList(entities);
  }

  @Override
  public MaterialResponseDto findById(final Integer id) {
    final Optional<MaterialEntity> entityOptional = this.materialRepository.findById(id);
    return entityOptional.map(this.materialFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public MaterialResponseDto create(final MaterialRequestDto requestDto) {
    final MaterialEntity entity = this.materialRepository.save(this.materialFieldsMapper.toEntity(requestDto));
    return this.materialFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(Integer id) {
    final MaterialEntity existingEntity = this.materialRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.materialRepository.deleteById(existingEntity.getId());
  }
}
