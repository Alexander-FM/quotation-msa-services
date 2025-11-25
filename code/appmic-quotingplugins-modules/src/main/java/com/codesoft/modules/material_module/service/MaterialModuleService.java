package com.codesoft.modules.material_module.service;

import java.util.List;

import com.codesoft.modules.material_module.dto.request.MaterialModuleRequestDto;
import com.codesoft.modules.material_module.dto.response.MaterialModuleResponseDto;

public interface MaterialModuleService {

  List<MaterialModuleResponseDto> findAll();

  MaterialModuleResponseDto findById(final Integer id);

  List<MaterialModuleResponseDto> findByModuleName(final String moduleName);

  MaterialModuleResponseDto create(final MaterialModuleRequestDto requestDto);

  void deleteById(final Integer id);
}
