package com.codesoft.materials.material.client.unit_of_measurement.service;

import com.codesoft.materials.material.client.unit_of_measurement.dto.UnitOfMeasurementResponseDto;

public interface UnitOfMeasurementClient {

  UnitOfMeasurementResponseDto searchByName(final String name);
}
