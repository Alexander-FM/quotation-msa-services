package com.codesoft.quotings.module.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "module_materials")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleMaterialEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "module_id", nullable = false, foreignKey = @ForeignKey(name = "fk_materials_module"))
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private ModuleEntity module;

  @Column(name = "material_id", nullable = false)
  private Integer materialId; // referencia al micro servicio de materiales

  @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
  private BigDecimal quantity;

  @Column(name = "description_ref", length = 150)
  private String descriptionRef;

  @Column(name = "default_yield")
  private Integer defaultYield;

  @Column(name = "default_width", precision = 10, scale = 2)
  private BigDecimal defaultWidth;

  @Column(name = "default_length", precision = 10, scale = 2)
  private BigDecimal defaultLength;
}

