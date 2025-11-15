package com.codesoft.catalogs.catalog_item.utils;

import com.codesoft.utils.CategoryEmun;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryEnumConverter implements AttributeConverter<CategoryEmun, String> {

  @Override
  public String convertToDatabaseColumn(final CategoryEmun category) {
    if (category == null) {
      return null;
    }
    // Guarda en la BD el valor en minúscula ("document_type")
    return category.getValue();
  }

  @Override
  public CategoryEmun convertToEntityAttribute(final String dbData) {
    if (dbData == null) {
      return null;
    }
    // Lee de la BD y usa tu lógica flexible para encontrar el Enum
    return CategoryEmun.fromValue(dbData);
  }
}
