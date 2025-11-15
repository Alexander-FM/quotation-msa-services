package com.codesoft.catalogs.catalog_item.mapper;

import java.util.List;

import com.codesoft.catalogs.catalog_item.dto.request.CatalogItemRequestDto;
import com.codesoft.catalogs.catalog_item.dto.response.CatalogItemResponseDto;
import com.codesoft.catalogs.catalog_item.model.entity.CatalogItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogItemFieldsMapper {

  CatalogItemEntity toEntity(final CatalogItemRequestDto source);

  CatalogItemResponseDto toDto(final CatalogItemEntity destination);

  List<CatalogItemResponseDto> toDtoList(final List<CatalogItemEntity> entityList);
}
