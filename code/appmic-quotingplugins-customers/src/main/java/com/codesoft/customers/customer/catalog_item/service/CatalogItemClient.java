package com.codesoft.customers.customer.catalog_item.service;

import com.codesoft.customers.customer.catalog_item.dto.CatalogItemResponseDto;

public interface CatalogItemClient {

  CatalogItemResponseDto searchByDocumentTypeCode(final String code);
}
