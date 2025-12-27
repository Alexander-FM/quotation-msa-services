package com.codesoft.materials.material.service;

import java.util.List;
import java.util.Set;

import com.codesoft.materials.material.dto.request.MaterialRequestDto;
import com.codesoft.materials.material.dto.response.MaterialResponseDto;

public interface MaterialService {

  List<MaterialResponseDto> findAll();

  List<MaterialResponseDto> findAllById(final Set<Integer> idList);

  MaterialResponseDto findById(final Integer id);

  MaterialResponseDto create(final MaterialRequestDto requestDto);

  void deleteById(final Integer id);

}
