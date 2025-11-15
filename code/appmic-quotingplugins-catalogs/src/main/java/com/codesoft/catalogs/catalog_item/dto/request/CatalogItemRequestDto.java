package com.codesoft.catalogs.catalog_item.dto.request;

import com.codesoft.utils.CategoryEmun;
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
public class CatalogItemRequestDto {

  private Integer id;

  @NotNull(message = "The category must not be null.")
  private CategoryEmun category;

  @NotNull(message = "The code must not be null.")
  @NotEmpty(message = "The code must not be empty.")
  private String code;

  private String description;

  private Boolean isActive;
}
