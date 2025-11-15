package com.codesoft.catalogs.catalog_item.dto.response;

import com.codesoft.utils.CategoryEmun;
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

  private CategoryEmun category;

  private String code;

  private String description;

  private Boolean isActive;
}
