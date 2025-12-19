package com.codesoft.quotations.quotation.dto.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class QuotationResponseDto {

  private Integer id;

  private String customerDocumentNumber;

  private String customerName;

  private String employeeDocumentNumber;

  private String employeeName;

  private String state;

  private BigDecimal totalProductionCost;

  private BigDecimal totalFinalPrice;

  private Set<QuotationDetailResponseDto> details = new HashSet<>();
}
