package com.codesoft.quotings.quoting.service;

import java.util.List;

import com.codesoft.quotings.quoting.dto.request.QuotingRequestDto;
import com.codesoft.quotings.quoting.dto.response.QuotingResponseDto;

public interface QuotingService {

  List<QuotingResponseDto> findAll();

  QuotingResponseDto findById(final Integer id);

  QuotingResponseDto create(final QuotingRequestDto requestDto);

  void deleteById(final Integer id);

}
