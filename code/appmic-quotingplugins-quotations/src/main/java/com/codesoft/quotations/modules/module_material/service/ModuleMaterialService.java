package com.codesoft.quotations.modules.module_material.service;

import java.util.List;

import com.codesoft.quotations.modules.module_material.dto.request.ModuleMaterialRequestDto;
import com.codesoft.quotations.modules.module_material.dto.response.ModuleMaterialResponseDto;

public interface ModuleMaterialService {

  List<ModuleMaterialResponseDto> findAll();

  ModuleMaterialResponseDto findById(final Integer id);

  ModuleMaterialResponseDto create(final ModuleMaterialRequestDto requestDto);

  void deleteById(final Integer id);

}
