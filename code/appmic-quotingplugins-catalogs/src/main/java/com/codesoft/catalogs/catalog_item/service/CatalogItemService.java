package com.codesoft.catalogs.catalog_item.service;

import java.util.List;

import com.codesoft.catalogs.catalog_item.dto.request.CatalogItemRequestDto;
import com.codesoft.catalogs.catalog_item.dto.response.CatalogItemResponseDto;

public interface CatalogItemService {

  List<CatalogItemResponseDto> findAll();

  CatalogItemResponseDto findById(final Integer id);

  CatalogItemResponseDto findByCode(final String code);

  CatalogItemResponseDto create(final CatalogItemRequestDto requestDto);

  void deleteById(final Integer id);

}
