package com.codesoft.employees.employee.client.catalog_item.service;

import com.codesoft.employees.employee.client.catalog_item.dto.CatalogItemResponseDto;

public interface CatalogItemClient {

  CatalogItemResponseDto searchByDocumentTypeCode(final String code);
}
