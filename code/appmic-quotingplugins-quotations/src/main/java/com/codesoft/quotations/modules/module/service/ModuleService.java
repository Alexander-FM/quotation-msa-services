package com.codesoft.quotations.modules.module.service;

import java.util.List;

import com.codesoft.quotations.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.quotations.modules.module.dto.response.ModuleResponseDto;

public interface ModuleService {

  List<ModuleResponseDto> findAll();

  ModuleResponseDto findById(final Integer id);

  ModuleResponseDto create(final ModuleRequestDto requestDto);

  void deleteById(final Integer id);

}
