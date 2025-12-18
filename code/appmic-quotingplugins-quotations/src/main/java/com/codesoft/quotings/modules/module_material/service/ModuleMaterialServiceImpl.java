package com.codesoft.quotings.modules.module_material.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ModuleMaterialServiceImpl implements ModuleMaterialService {

  @Override
  public List<ModuleMaterialResponseDto> findAll() {
    return List.of();
  }

  @Override
  public ModuleMaterialResponseDto findById(Integer id) {
    return null;
  }

  @Override
  public ModuleMaterialResponseDto create(ModuleMaterialRequestDto requestDto) {
    return null;
  }

  @Override
  public void deleteById(Integer id) {

  }
}
