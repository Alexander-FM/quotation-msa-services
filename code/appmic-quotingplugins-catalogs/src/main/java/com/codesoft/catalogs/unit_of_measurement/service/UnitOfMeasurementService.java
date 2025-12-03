package com.codesoft.catalogs.unit_of_measurement.service;

import java.util.List;

import com.codesoft.catalogs.unit_of_measurement.dto.request.UnitOfMeasurementRequestDto;
import com.codesoft.catalogs.unit_of_measurement.dto.response.UnitOfMeasurementResponseDto;

public interface UnitOfMeasurementService {

  List<UnitOfMeasurementResponseDto> findAll();

  UnitOfMeasurementResponseDto findById(final Integer id);

  UnitOfMeasurementResponseDto findByName(final String name);

  UnitOfMeasurementResponseDto create(final UnitOfMeasurementRequestDto requestDto);

  void deleteById(final Integer id);

}
