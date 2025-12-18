package com.codesoft.quotings.client.material.utils;

import java.util.Arrays;

import com.codesoft.quotings.quoting.exception.QuotingException;
import com.codesoft.quotings.quoting.exception.QuotingMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
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

  @JsonCreator
  public static MaterialCalculationTypeEnum fromValue(final String description) {
    if (description == null) {
      return null;
    }
    return Arrays.stream(values())
      .filter(c -> c.description.equalsIgnoreCase(description) || c.name().equalsIgnoreCase(description))
      .findFirst()
      .orElseThrow(() -> new QuotingException(QuotingMessage.MODULE_MATERIAL_CALCULATION_TYPE_NOT_FOUND));
  }
}
