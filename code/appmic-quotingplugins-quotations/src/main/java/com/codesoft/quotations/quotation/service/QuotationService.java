package com.codesoft.quotations.quotation.service;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;

public interface QuotationService {

  List<QuotationResponseDto> findAll();

  QuotationResponseDto findById(final Integer id);

  QuotationResponseDto create(final QuotationRequestDto requestDto);

  void deleteById(final Integer id);

}
