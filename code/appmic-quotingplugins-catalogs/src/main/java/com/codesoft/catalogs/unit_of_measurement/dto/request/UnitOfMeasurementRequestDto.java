package com.codesoft.catalogs.unit_of_measurement.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasurementRequestDto {

  private Integer id;

  @NotNull(message = "El nombre no puede ser nulo.")
  @NotEmpty(message = "El nombre no puede estar vacío.")
  private String name;

  private String description;

  private Boolean isActive;
}
