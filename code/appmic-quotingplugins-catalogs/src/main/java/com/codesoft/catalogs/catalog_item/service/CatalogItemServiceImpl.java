package com.codesoft.catalogs.catalog_item.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.catalogs.catalog_item.dto.request.CatalogItemRequestDto;
import com.codesoft.catalogs.catalog_item.dto.response.CatalogItemResponseDto;
import com.codesoft.catalogs.catalog_item.exception.CatalogItemException;
import com.codesoft.catalogs.catalog_item.exception.CatalogItemMessage;
import com.codesoft.catalogs.catalog_item.mapper.CatalogItemFieldsMapper;
import com.codesoft.catalogs.catalog_item.model.entity.CatalogItemEntity;
import com.codesoft.catalogs.catalog_item.repository.CatalogItemRepository;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogItemServiceImpl implements CatalogItemService {

  private final CatalogItemRepository catalogItemRepository;

  private final CatalogItemFieldsMapper catalogItemFieldsMapper;

  @Override
  public List<CatalogItemResponseDto> findAll() {
    final List<CatalogItemEntity> entities = catalogItemRepository.findAll().stream().toList();
    return catalogItemFieldsMapper.toDtoList(entities);
  }

  @Override
  public CatalogItemResponseDto findById(final Integer id) {
    final Optional<CatalogItemEntity> entityOptional = this.catalogItemRepository.findById(id);
    return entityOptional.map(this.catalogItemFieldsMapper::toDto)
      .orElseThrow(() -> new CatalogItemException(CatalogItemMessage.CATALOG_ITEM_NOT_FOUND));
  }

  @Override
  public CatalogItemResponseDto findByCode(final String code) {
    final Optional<CatalogItemEntity> entityOptional = this.catalogItemRepository.findByCodeAndIsActiveTrue(code);
    return entityOptional.map(this.catalogItemFieldsMapper::toDto)
      .orElseThrow(() -> new CatalogItemException(CatalogItemMessage.CATALOG_ITEM_NOT_FOUND));
  }

  @Override
  public CatalogItemResponseDto create(final CatalogItemRequestDto requestDto) {
    try {
      final CatalogItemEntity entity = this.catalogItemRepository.save(this.catalogItemFieldsMapper.toEntity(requestDto));
      return this.catalogItemFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException ex) {
      log.error("Data integrity violation when creating catalog item: {}", ex.getMessage());
      throw new CatalogItemException(CatalogItemMessage.CATALOG_ITEM_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    final CatalogItemEntity existingEntity = this.catalogItemRepository.findById(id)
      .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.catalogItemRepository.deleteById(existingEntity.getId());
  }
}
