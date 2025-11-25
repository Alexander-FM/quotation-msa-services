package com.codesoft.modules.module_concepts.service;

import java.util.List;

import com.codesoft.modules.module_concepts.dto.request.ModuleConceptsRequestDto;
import com.codesoft.modules.module_concepts.dto.response.ModuleConceptsResponseDto;

public interface ModuleConceptsService {

  List<ModuleConceptsResponseDto> findAll();

  ModuleConceptsResponseDto findById(final Integer id);

  ModuleConceptsResponseDto findByModuleName(final String moduleName);

  ModuleConceptsResponseDto create(final ModuleConceptsRequestDto requestDto);

  void deleteById(final Integer id);
}
