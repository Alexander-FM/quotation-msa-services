package com.codesoft.catalogs.adjustment_factor.model.entity;

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
@Table(
  name = "adjustment_factor",
  uniqueConstraints = @UniqueConstraint(name = "unique_adjustment_factor_name", columnNames = "name")
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentFactorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adjustment_factor_seq")
  @SequenceGenerator(name = "adjustment_factor_seq", sequenceName = "adjustment_factor_id_seq", allocationSize = 1)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "value", precision = 10, scale = 2, nullable = false)
  private BigDecimal value;

  @Column(name = "is_active")
  private Boolean isActive = true;
}
