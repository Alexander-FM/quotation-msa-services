package com.codesoft.materials.material.client.unit_of_measurement.dto;

import lombok.Data;

@Data
public class UnitOfMeasurementResponseDto {

  private Integer id;

  private String name;

  private String description;

  private Boolean isActive;
}
