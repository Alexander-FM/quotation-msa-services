package com.codesoft.catalogs.catalog_item.utils;

import com.codesoft.utils.CategoryEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryEnumConverter implements AttributeConverter<CategoryEnum, String> {

  @Override
  public String convertToDatabaseColumn(final CategoryEnum category) {
    if (category == null) {
      return null;
    }
    // Guarda en la BD el valor en minúscula ("document_type")
    return category.getValue();
  }

  @Override
  public CategoryEnum convertToEntityAttribute(final String dbData) {
    if (dbData == null) {
      return null;
    }
    // Lee de la BD y usa tu lógica flexible para encontrar el Enum
    return CategoryEnum.fromValue(dbData);
  }
}
