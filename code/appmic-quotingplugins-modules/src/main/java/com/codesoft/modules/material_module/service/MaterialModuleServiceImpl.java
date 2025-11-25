package com.codesoft.modules.material_module.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.exception.BaseException;
import com.codesoft.modules.material_module.dto.request.MaterialModuleRequestDto;
import com.codesoft.modules.material_module.dto.response.MaterialModuleResponseDto;
import com.codesoft.modules.material_module.mapper.MaterialModuleFieldsMapper;
import com.codesoft.modules.material_module.model.entity.MaterialModuleEntity;
import com.codesoft.modules.material_module.repository.MaterialModuleRepository;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialModuleServiceImpl implements MaterialModuleService {

  private final MaterialModuleRepository materialModuleRepository;

  private final MaterialModuleFieldsMapper materialModuleFieldsMapper;

  @Override
  public List<MaterialModuleResponseDto> findAll() {
    final List<MaterialModuleEntity> entities = materialModuleRepository.findAll().stream().toList();
    return materialModuleFieldsMapper.toDtoList(entities);
  }

  @Override
  public MaterialModuleResponseDto findById(final Integer id) {
    final Optional<MaterialModuleEntity> entityOptional = this.materialModuleRepository.findById(id);
    return entityOptional.map(this.materialModuleFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public List<MaterialModuleResponseDto> findByModuleName(final String moduleName) {
    final List<MaterialModuleEntity> entities = this.materialModuleRepository.findByModuleName(moduleName);
    return materialModuleFieldsMapper.toDtoList(entities);
  }

  @Override
  public MaterialModuleResponseDto create(final MaterialModuleRequestDto requestDto) {
    final MaterialModuleEntity entity = this.materialModuleRepository.save(this.materialModuleFieldsMapper.toEntity(requestDto));
    return this.materialModuleFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(Integer id) {
    final MaterialModuleEntity existingEntity = this.materialModuleRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.materialModuleRepository.deleteById(existingEntity.getId());
  }
}
