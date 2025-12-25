package com.codesoft.quotations.modules.module.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "modules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", length = 100, nullable = false, unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "dimensions", length = 100)
  private String dimensions;

  @Column(name = "is_active")
  private Boolean isActive = true;

  @Column(name = "overheads_cost_percentage", precision = 5, scale = 2)
  private BigDecimal overheadsCostPercentage = BigDecimal.ZERO;

  @Column(name = "fee_percentage", precision = 5, scale = 2)
  private BigDecimal feePercentage = BigDecimal.ZERO;

  @Column(name = "rebate_percentage", precision = 5, scale = 2)
  private BigDecimal rebatePercentage = BigDecimal.ZERO;

  @Column(name = "profit_margin_percentage", precision = 5, scale = 2)
  private BigDecimal profitMarginPercentage = BigDecimal.ZERO;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
}

