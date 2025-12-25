package com.codesoft.materials.material.model.entity;

import java.math.BigDecimal;

import com.codesoft.materials.material.utils.MaterialCalculationTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "materials",
  uniqueConstraints = @UniqueConstraint(name = "unique_materials_thickness_microns", columnNames = "thickness_microns")
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materials_seq")
  @SequenceGenerator(name = "materials_seq", sequenceName = "materials_id_seq", allocationSize = 1)
  private Integer id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column
  private String description;

  @Column(name = "unit_cost", precision = 10, scale = 2, nullable = false)
  private BigDecimal unitCost;

  @Column(name = "unit_of_measurement_name", nullable = false, length = 20)
  private String unidadOfMeasurementName;

  @Column(name = "calculation_type", length = 30, nullable = false)
  @Enumerated(EnumType.STRING)
  private MaterialCalculationTypeEnum calculationType;

  @Column(name = "adjustment_factor_name", length = 100)
  private String adjustmentFactorName;

  @Column(name = "adjustment_factor_value", precision = 10, scale = 2)
  private BigDecimal adjustmentFactorValue;

  @Column(name = "thickness_microns")
  private Integer thicknessMicrons;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

}
