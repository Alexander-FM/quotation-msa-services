package com.codesoft.quotings.quoting.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuotingServiceImpl implements QuotingService {

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
