package com.codesoft.modules.module_concepts.model.entity;

import java.math.BigDecimal;

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
@Table(name = "module_concepts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleConceptsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_concepts_seq")
  @SequenceGenerator(name = "module_concepts_seq", sequenceName = "module_concepts_id_seq", allocationSize = 1)
  private Integer id;

  @Column(name = "overheads_cost", nullable = false)
  private Short overheadsCost;

  @Column
  private Short fee;

  @Column
  private Short rebate;

  @Column(name = "profit_margin")
  private BigDecimal profitMargin;

  @Column(name = "module_name", nullable = false, length = 100)
  private String moduleName;
}
