package com.codesoft.quotings.modules.module_material.service;

import java.util.List;

public interface ModuleMaterialService {

  List<ModuleMaterialResponseDto> findAll();

  ModuleMaterialResponseDto findById(final Integer id);

  ModuleMaterialResponseDto create(final ModuleMaterialRequestDto requestDto);

  void deleteById(final Integer id);

}
