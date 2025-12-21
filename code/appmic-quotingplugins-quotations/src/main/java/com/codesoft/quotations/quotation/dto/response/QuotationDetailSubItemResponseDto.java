package com.codesoft.quotations.quotation.dto.response;

import java.math.BigDecimal;

import com.codesoft.quotations.client.material.dto.MaterialResponseDto;
import lombok.Data;

@Data
public class QuotationDetailSubItemResponseDto {

  private Integer id;

  private MaterialResponseDto material;

  private BigDecimal quantity;

  private BigDecimal rawMaterialCost;

  private Integer pieces;

  private BigDecimal unitPrice;

  private BigDecimal totalPrice;
}
