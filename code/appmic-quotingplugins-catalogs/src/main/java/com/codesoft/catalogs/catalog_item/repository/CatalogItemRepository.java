package com.codesoft.catalogs.catalog_item.repository;

import java.util.Optional;

import com.codesoft.catalogs.catalog_item.model.entity.CatalogItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItemEntity, Integer> {

  /**
   * Find the document type or identification_type by code and is active true.
   *
   * @param code the code.
   * @return the optional catalog item entity.
   */
  Optional<CatalogItemEntity> findByCodeAndIsActiveTrue(final String code);
}
