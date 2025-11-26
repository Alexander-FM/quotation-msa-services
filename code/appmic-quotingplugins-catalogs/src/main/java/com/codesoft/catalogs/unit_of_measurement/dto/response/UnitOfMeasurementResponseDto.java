package com.codesoft.catalogs.unit_of_measurement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasurementResponseDto {

  private Integer id;

  private String name;

  private String description;

  private Boolean isActive;
}
