package com.codesoft.catalogs.unit_of_measurement.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "unit_of_measurement",
  uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class UnitOfMeasurementEntity {

  @Id
  @SequenceGenerator(name = "uom_seq", sequenceName = "unit_of_measurement_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uom_seq")
  private Integer id;

  @Column(name = "name", nullable = false, unique = true, length = 255)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "is_active", columnDefinition = "boolean default true")
  private Boolean isActive;
}
