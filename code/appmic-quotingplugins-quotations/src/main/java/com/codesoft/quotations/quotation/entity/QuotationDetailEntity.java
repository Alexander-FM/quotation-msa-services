package com.codesoft.quotations.quotation.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.codesoft.quotations.modules.module.entity.ModuleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "quotation_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quotation_id", nullable = false)
  @ToString.Exclude
  private QuotationEntity quotation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "module_id", nullable = false)
  private ModuleEntity module;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  // Costos Fijos
  @Column(name = "transportation_cost", precision = 10, scale = 2)
  private BigDecimal transportationCost;

  @Column(name = "labor_cost", precision = 10, scale = 2)
  private BigDecimal laborCost;

  @Column(name = "packing_cost", precision = 10, scale = 2)
  private BigDecimal packingCost;

  // === GASTOS GENERALES ===
  @Column(name = "overheads_percentage", precision = 5, scale = 2)
  private BigDecimal overheadsPercentage;

  @Column(name = "overheads_amount", precision = 10, scale = 2)
  private BigDecimal overheadsAmount;

  // === FEE ===
  @Column(name = "fee_percentage", precision = 5, scale = 2)
  private BigDecimal feePercentage;

  @Column(name = "fee_amount", precision = 10, scale = 2)
  private BigDecimal feeAmount;

  // === REBATE ===
  @Column(name = "rebate_percentage", precision = 5, scale = 2)
  private BigDecimal rebatePercentage;

  @Column(name = "rebate_amount", precision = 10, scale = 2)
  private BigDecimal rebateAmount;

  // === MARGEN UTILIDAD ===
  @Column(name = "profit_margin_percentage", precision = 5, scale = 2)
  private BigDecimal profitMarginPercentage;

  @Column(name = "profit_margin_amount", precision = 10, scale = 2)
  private BigDecimal profitMarginAmount;

  @Column(name = "unit_final_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal unitFinalPrice;

  @Column(name = "total_line_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal totalLinePrice;

  // Relación con Sub-Items (Materiales)
  @OneToMany(mappedBy = "quotationDetail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<QuotationDetailSubItemEntity> subItems = new HashSet<>();

  /**
   * Añade un sub-item (material) al detalle de la cotización y establece la relación bidireccional.
   */
  public void addSubItem(final QuotationDetailSubItemEntity subItem) {
    subItems.add(subItem);
    subItem.setQuotationDetail(this);
  }
}