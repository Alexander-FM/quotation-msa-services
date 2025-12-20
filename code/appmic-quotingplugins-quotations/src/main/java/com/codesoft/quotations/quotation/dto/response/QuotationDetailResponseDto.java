package com.codesoft.quotations.quotation.dto.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.codesoft.quotations.modules.module.dto.response.ModuleResponseDto;
import lombok.Data;

@Data
public class QuotationDetailResponseDto {

  private Integer id;

  private QuotationResponseDto quotation;

  private ModuleResponseDto module;

  private Integer quantity;

  private BigDecimal transportationCost;

  private BigDecimal laborCost;

  private BigDecimal packingCost;

  private BigDecimal overheadsPercentage;

  private BigDecimal overheadsAmount;

  private BigDecimal feePercentage;

  private BigDecimal feeAmount;

  private BigDecimal rebatePercentage;

  private BigDecimal rebateAmount;

  private BigDecimal profitMarginPercentage;

  private BigDecimal profitMarginAmount;

  private BigDecimal unitFinalPrice;

  private BigDecimal totalLinePrice;

  private Set<QuotationDetailSubItemResponseDto> subItems = new HashSet<>();
}
