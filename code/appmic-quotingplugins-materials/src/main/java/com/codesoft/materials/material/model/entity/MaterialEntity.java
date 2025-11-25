package com.codesoft.materials.material.model.entity;

import java.math.BigDecimal;

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
@Table(name = "materials", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materials_seq")
  @SequenceGenerator(name = "materials_seq", sequenceName = "materials_id_seq", allocationSize = 1)
  private Integer id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(length = 255)
  private String description;

  @Column(name = "unit_cost", nullable = false)
  private BigDecimal unitCost;

  @Column
  private BigDecimal width;

  @Column
  private BigDecimal length;

  @Column
  private BigDecimal height;

  @Column(name = "used_formula", nullable = false)
  private Boolean usedFormula;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @Column(name = "unidad_name", nullable = false, length = 20)
  private String unidadName;

  @Column(name = "adjustment_factor_name", length = 40)
  private String adjustmentFactorName;
}
