package com.codesoft.modules.material_module.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_module")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialModuleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_module_seq")
  @SequenceGenerator(name = "material_module_seq", sequenceName = "material_module_id_seq", allocationSize = 1)
  private Integer id;

  @Column(nullable = false)
  private Short quantity;

  @Column
  private Short pieces;

  @Column(name = "module_name", nullable = false, length = 100)
  private String moduleName;

  @Column(name = "material_name", length = 50)
  private String materialName;
}
