package com.codesoft.quotations.quotation.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "quotation_detail_subitem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDetailSubItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "quotation_detail_id", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private QuotationDetailEntity quotationDetail;

  @Column(name = "material_id", nullable = false)
  private Integer materialId;

  @Column(name = "quantity", nullable = false, precision = 12, scale = 4)
  private BigDecimal quantity;

  @Column(name = "raw_material_cost", nullable = false, precision = 10, scale = 4)
  private BigDecimal rawMaterialCost;

  @Column(name = "pieces")
  private Integer pieces;

  @Column(name = "unit_price", nullable = false, precision = 10, scale = 4)
  private BigDecimal unitPrice;

  @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal totalPrice;

  public void calculateUnitPrice() {
    // Si hay piezas > 0 (ej. Plancha), dividimos.
    // Si es 0 o null (ej. Alambre/Kg), el unitPrice es el rawMaterialCost.
    if (this.rawMaterialCost != null) {
      if (this.pieces != null && this.pieces > 0) {
        this.unitPrice = this.rawMaterialCost.divide(BigDecimal.valueOf(this.pieces), 4, RoundingMode.HALF_UP);
      } else {
        this.unitPrice = this.rawMaterialCost;
      }
    } else {
      this.unitPrice = BigDecimal.ZERO;
    }
  }

  public void calculateTotalPrice() {
    if (this.unitPrice == null || this.quantity == null) {
      this.totalPrice = BigDecimal.ZERO;
      return;
    }
    this.totalPrice = this.unitPrice.multiply(this.quantity).setScale(2, RoundingMode.HALF_UP);
  }
}