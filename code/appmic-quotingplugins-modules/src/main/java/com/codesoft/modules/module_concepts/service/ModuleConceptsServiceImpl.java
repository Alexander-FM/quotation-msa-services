package com.codesoft.modules.module_concepts.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.exception.BaseException;
import com.codesoft.modules.module_concepts.dto.request.ModuleConceptsRequestDto;
import com.codesoft.modules.module_concepts.dto.response.ModuleConceptsResponseDto;
import com.codesoft.modules.module_concepts.mapper.ModuleConceptsFieldsMapper;
import com.codesoft.modules.module_concepts.model.entity.ModuleConceptsEntity;
import com.codesoft.modules.module_concepts.repository.ModuleConceptsRepository;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleConceptsServiceImpl implements ModuleConceptsService {

  private final ModuleConceptsRepository moduleConceptsRepository;

  private final ModuleConceptsFieldsMapper moduleConceptsFieldsMapper;

  @Override
  public List<ModuleConceptsResponseDto> findAll() {
    final List<ModuleConceptsEntity> entities = moduleConceptsRepository.findAll().stream().toList();
    return moduleConceptsFieldsMapper.toDtoList(entities);
  }

  @Override
  public ModuleConceptsResponseDto findById(final Integer id) {
    final Optional<ModuleConceptsEntity> entityOptional = this.moduleConceptsRepository.findById(id);
    return entityOptional.map(this.moduleConceptsFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public ModuleConceptsResponseDto findByModuleName(final String moduleName) {
    final Optional<ModuleConceptsEntity> entityOptional = this.moduleConceptsRepository.findByModuleName(moduleName);
    return entityOptional.map(this.moduleConceptsFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public ModuleConceptsResponseDto create(final ModuleConceptsRequestDto requestDto) {
    final ModuleConceptsEntity entity = this.moduleConceptsRepository.save(this.moduleConceptsFieldsMapper.toEntity(requestDto));
    return this.moduleConceptsFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(Integer id) {
    final ModuleConceptsEntity existingEntity = this.moduleConceptsRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.moduleConceptsRepository.deleteById(existingEntity.getId());
  }
}
