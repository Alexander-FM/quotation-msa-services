package com.codesoft.quotings.quoting.service;

import java.util.List;

import com.codesoft.quotings.quoting.dto.request.QuotingRequestDto;
import com.codesoft.quotings.quoting.dto.response.QuotingResponseDto;
import org.springframework.stereotype.Service;

@Service
public class QuotingServiceImpl implements QuotingService {

  @Override
  public List<QuotingResponseDto> findAll() {
    return List.of();
  }

  @Override
  public QuotingResponseDto findById(Integer id) {
    return null;
  }

  @Override
  public QuotingResponseDto create(final QuotingRequestDto requestDto) {
    return null;
  }

  @Override
  public void deleteById(final Integer id) {

  }
}
