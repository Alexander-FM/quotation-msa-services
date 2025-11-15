package com.codesoft.catalogs.catalog_item.model.entity;

import com.codesoft.utils.CategoryEmun;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catalog_item", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catalog_item_seq")
  @SequenceGenerator(name = "catalog_item_seq", sequenceName = "catalog_item_id_seq", allocationSize = 1)
  private Integer id;

  @Column(nullable = false, length = 50)
  private CategoryEmun category;

  @Column(nullable = false, length = 20, unique = true)
  private String code;

  @Column
  private String description;

  @Column(name = "is_active")
  private Boolean isActive;
}
