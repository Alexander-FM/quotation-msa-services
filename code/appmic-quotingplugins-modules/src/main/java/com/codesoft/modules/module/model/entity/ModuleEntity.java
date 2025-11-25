package com.codesoft.modules.module.model.entity;

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
@Table(name = "module", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_seq")
  @SequenceGenerator(name = "module_seq", sequenceName = "module_id_seq", allocationSize = 1)
  private Integer id;

  @Column(nullable = false, length = 100, unique = true)
  private String name;

  @Column(length = 500)
  private String dimensions;

  @Column(name = "is_active")
  private Boolean isActive;
}
