package com.codesoft.quotings.modules.module.service;

import java.util.List;

import com.codesoft.quotings.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.quotings.modules.module.dto.response.ModuleResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

  @Override
  public List<ModuleResponseDto> findAll() {
    return List.of();
  }

  @Override
  public ModuleResponseDto findById(final Integer id) {
    return null;
  }

  @Override
  public ModuleResponseDto create(final ModuleRequestDto requestDto) {
    return null;
  }

  @Override
  public void deleteById(final Integer id) {
    // TODO document why this method is empty
  }
}
