package com.codesoft.quotations.modules.module_material.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.quotations.modules.module_material.dto.request.ModuleMaterialRequestDto;
import com.codesoft.quotations.modules.module_material.dto.response.ModuleMaterialResponseDto;
import com.codesoft.quotations.modules.module_material.entity.ModuleMaterialEntity;
import com.codesoft.quotations.modules.module_material.exception.ModuleMaterialException;
import com.codesoft.quotations.modules.module_material.exception.ModuleMaterialMessage;
import com.codesoft.quotations.modules.module_material.mapper.ModuleMaterialFieldsMapper;
import com.codesoft.quotations.modules.module_material.repository.ModuleMaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleMaterialServiceImpl implements ModuleMaterialService {

  private final ModuleMaterialRepository moduleMaterialRepository;

  private final ModuleMaterialFieldsMapper moduleMaterialFieldsMapper;

  @Override
  public List<ModuleMaterialResponseDto> findAll() {
    final List<ModuleMaterialEntity> entities = this.moduleMaterialRepository.findAll();
    return moduleMaterialFieldsMapper.toDtoList(entities);
  }

  @Override
  public ModuleMaterialResponseDto findById(final Integer id) {
    final Optional<ModuleMaterialEntity> entityOptional = this.moduleMaterialRepository.findById(id);
    return entityOptional.map(this.moduleMaterialFieldsMapper::toDto)
      .orElseThrow(() -> new ModuleMaterialException(ModuleMaterialMessage.MODULE_MATERIAL_NOT_FOUND));
  }

  @Override
  public ModuleMaterialResponseDto create(final ModuleMaterialRequestDto requestDto) {
    try {
      final ModuleMaterialEntity entity = this.moduleMaterialRepository.save(this.moduleMaterialFieldsMapper.toEntity(requestDto));
      return this.moduleMaterialFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException ex) {
      log.warn("Data integrity violation when creating material for the module: {}", ex.getMessage());
      throw new ModuleMaterialException(ModuleMaterialMessage.MODULE_MATERIAL_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.moduleMaterialRepository.findById(id)
      .orElseThrow(() -> new ModuleMaterialException(ModuleMaterialMessage.MODULE_MATERIAL_NOT_FOUND));
    this.moduleMaterialRepository.deleteById(id);
  }
}
