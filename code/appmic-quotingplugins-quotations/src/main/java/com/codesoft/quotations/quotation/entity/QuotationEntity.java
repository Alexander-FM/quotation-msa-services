package com.codesoft.quotations.quotation.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "quotation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "customer_document_number", nullable = false, length = 20)
  private String customerDocumentNumber;

  @Column(name = "employee_document_number", nullable = false, length = 20)
  private String employeeDocumentNumber;

  @CreationTimestamp
  @Column(name = "date", updatable = false)
  private LocalDateTime date;

  @Column(name = "state", nullable = false, length = 20)
  private String state; // BORRADOR, EN REVISIÓN, APROBADO, RECHAZADO

  // Totales Globales
  @Column(name = "total_production_cost", precision = 10, scale = 2)
  private BigDecimal totalProductionCost;

  @Column(name = "total_final_price", precision = 10, scale = 2)
  private BigDecimal totalFinalPrice;

  // Relación OneToMany (LAZY para evitar N+1 al listar cabeceras)
  // Cascade ALL permite guardar la cotización completa de un solo golpe
  @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @ToString.Exclude // Evita bucles infinitos en logs
  private Set<QuotationDetailEntity> details = new HashSet<>();

  /**
   * Adds a detail to the quotation.
   *
   * @param detail the detail to add
   */
  public void addDetail(final QuotationDetailEntity detail) {
    details.add(detail);
    detail.setQuotation(this);
  }
}

