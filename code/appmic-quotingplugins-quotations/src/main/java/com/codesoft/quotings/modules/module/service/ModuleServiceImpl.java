package com.codesoft.quotings.modules.module.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.quotings.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.quotings.modules.module.dto.response.ModuleResponseDto;
import com.codesoft.quotings.modules.module.entity.ModuleEntity;
import com.codesoft.quotings.modules.module.exception.ModuleException;
import com.codesoft.quotings.modules.module.exception.ModuleMessage;
import com.codesoft.quotings.modules.module.mapper.ModuleFieldsMapper;
import com.codesoft.quotings.modules.module.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

  private final ModuleRepository moduleRepository;

  private final ModuleFieldsMapper moduleFieldsMapper;

  @Override
  public List<ModuleResponseDto> findAll() {
    final List<ModuleEntity> entities = this.moduleRepository.findAll();
    return moduleFieldsMapper.toDtoList(entities);
  }

  @Override
  public ModuleResponseDto findById(final Integer id) {
    final Optional<ModuleEntity> entityOptional = this.moduleRepository.findById(id);
    return entityOptional.map(this.moduleFieldsMapper::toDto).orElseThrow(() -> new ModuleException(ModuleMessage.MODULE_NOT_FOUND));
  }

  @Override
  public ModuleResponseDto create(final ModuleRequestDto requestDto) {
    try {
      final ModuleEntity entity = this.moduleRepository.save(this.moduleFieldsMapper.toEntity(requestDto));
      return this.moduleFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException ex) {
      log.warn("Data integrity violation when creating module: {}", ex.getMessage());
      throw new ModuleException(ModuleMessage.MODULE_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.moduleRepository.findById(id)
      .orElseThrow(() -> new ModuleException(ModuleMessage.MODULE_NOT_FOUND));
    this.moduleRepository.deleteById(id);
  }
}
