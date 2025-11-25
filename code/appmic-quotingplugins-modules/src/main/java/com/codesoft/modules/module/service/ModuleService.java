package com.codesoft.modules.module.service;

import java.util.List;

import com.codesoft.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.modules.module.dto.response.ModuleResponseDto;

public interface ModuleService {

  List<ModuleResponseDto> findAll();

  ModuleResponseDto findById(final Integer id);

  ModuleResponseDto findByName(final String name);

  ModuleResponseDto create(final ModuleRequestDto requestDto);

  void deleteById(final Integer id);
}
