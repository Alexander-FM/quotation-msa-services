package com.codesoft.catalogs.catalog_item.controller;

import java.util.List;

import com.codesoft.catalogs.catalog_item.dto.request.CatalogItemRequestDto;
import com.codesoft.catalogs.catalog_item.dto.response.CatalogItemResponseDto;
import com.codesoft.catalogs.catalog_item.service.CatalogItemService;
import com.codesoft.catalogs.catalog_item.utils.CatalogItemConstants;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalogs/catalog-item")
@RequiredArgsConstructor
public class CatalogItemController {

  private final CatalogItemService catalogItemService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<CatalogItemResponseDto>>> retrieve() {
    final List<CatalogItemResponseDto> responseDtoList = catalogItemService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(CatalogItemConstants.FOUND_MESSAGE, responseDtoList));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final CatalogItemResponseDto responseDto = catalogItemService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(CatalogItemConstants.FOUND_MESSAGE, responseDto));
  }

  @GetMapping("/searchByDocumentTypeCode")
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> retrieveByDocumentTypeCode(
    @RequestParam(name = "code") final String code) {
    final CatalogItemResponseDto responseDto = catalogItemService.findByCode(code);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(CatalogItemConstants.FOUND_MESSAGE, responseDto));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> create(@Valid @RequestBody final CatalogItemRequestDto requestDto) {
    if (requestDto.getId() != null) {
      throw new BaseException(BaseErrorMessage.ID_PROVIDED_ON_CREATE);
    }
    final CatalogItemResponseDto responseDto = this.catalogItemService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(GenericResponseUtils.buildGenericResponseSuccess(CatalogItemConstants.SAVED_MESSAGE, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<CatalogItemResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final CatalogItemRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final CatalogItemResponseDto existing = catalogItemService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.catalogItemService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(GenericResponseUtils.buildGenericResponseError(CatalogItemConstants.UPDATED_MESSAGE, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.catalogItemService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(CatalogItemConstants.REMOVED_MESSAGE, null));
  }
}
