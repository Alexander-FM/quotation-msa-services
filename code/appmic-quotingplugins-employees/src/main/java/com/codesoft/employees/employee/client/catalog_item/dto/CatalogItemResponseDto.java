package com.codesoft.employees.employee.client.catalog_item.dto;

import com.codesoft.utils.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItemResponseDto {

  private Integer id;

  private CategoryEnum category;

  private String code;

  private String description;

  private Boolean isActive;
}
