package com.codesoft.utils;

import java.util.Arrays;

import com.codesoft.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryEnum {
  DOCUMENT_TYPE("document_type"),
  IDENTIFICATION_TYPE("identification_type");

  @Getter
  @JsonValue
  private final String value;

  @JsonCreator
  public static CategoryEnum fromValue(final String value) {
    if (value == null) {
      return null;
    }
    return Arrays.stream(values())
        .filter(c -> c.value.equalsIgnoreCase(value) || c.name().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new BaseException(BaseErrorMessage.BAD_REQUEST));
  }
}
