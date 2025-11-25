package com.codesoft.modules.module.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.exception.BaseException;
import com.codesoft.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.modules.module.dto.response.ModuleResponseDto;
import com.codesoft.modules.module.mapper.ModuleFieldsMapper;
import com.codesoft.modules.module.model.entity.ModuleEntity;
import com.codesoft.modules.module.repository.ModuleRepository;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

  private final ModuleRepository moduleRepository;

  private final ModuleFieldsMapper moduleFieldsMapper;

  @Override
  public List<ModuleResponseDto> findAll() {
    final List<ModuleEntity> entities = moduleRepository.findAll().stream().toList();
    return moduleFieldsMapper.toDtoList(entities);
  }

  @Override
  public ModuleResponseDto findById(final Integer id) {
    final Optional<ModuleEntity> entityOptional = this.moduleRepository.findById(id);
    return entityOptional.map(this.moduleFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public ModuleResponseDto findByName(final String name) {
    final Optional<ModuleEntity> entityOptional = this.moduleRepository.findByNameAndIsActiveTrue(name);
    return entityOptional.map(this.moduleFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public ModuleResponseDto create(final ModuleRequestDto requestDto) {
    final ModuleEntity entity = this.moduleRepository.save(this.moduleFieldsMapper.toEntity(requestDto));
    return this.moduleFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(Integer id) {
    final ModuleEntity existingEntity = this.moduleRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.moduleRepository.deleteById(existingEntity.getId());
  }
}
