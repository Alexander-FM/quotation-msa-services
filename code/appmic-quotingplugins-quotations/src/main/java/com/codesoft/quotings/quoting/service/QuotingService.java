package com.codesoft.quotings.quoting.service;

import java.util.List;

public interface QuotingService {

  List<ModuleMaterialResponseDto> findAll();

  ModuleMaterialResponseDto findById(final Integer id);

  ModuleMaterialResponseDto create(final ModuleMaterialRequestDto requestDto);

  void deleteById(final Integer id);

}
