package com.codesoft.quotations.client.material.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialCalculationTypeEnum {
  SIMPLE("Simple"),
  YIELD_BASED("Basado en el rendimiento"),
  VOLUME_DM("Volumen en decímetros cúbicos"),
  AREA("Area");

  private final String description;
}
