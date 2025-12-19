package com.codesoft.quotations.quotation.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationEntity;
import com.codesoft.quotations.quotation.exception.QuotationException;
import com.codesoft.quotations.quotation.exception.QuotationMessage;
import com.codesoft.quotations.quotation.mapper.QuotationDetailFieldsMapper;
import com.codesoft.quotations.quotation.mapper.QuotationDetailSubItemFieldsMapper;
import com.codesoft.quotations.quotation.mapper.QuotationFieldsMapper;
import com.codesoft.quotations.quotation.repository.QuotationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

  private final QuotationRepository quotationRepository;

  private final QuotationFieldsMapper quotationFieldsMapper;

  private final QuotationDetailFieldsMapper quotationDetailFieldsMapper;

  private final QuotationDetailSubItemFieldsMapper quotationDetailSubItemFieldsMapper;

  @Override
  public List<QuotationResponseDto> findAll() {
    final List<QuotationEntity> entities = this.quotationRepository.findAll();
    return quotationFieldsMapper.toDtoList(entities);
  }

  @Override
  public QuotationResponseDto findById(Integer id) {
    final Optional<QuotationEntity> entityOptional = this.quotationRepository.findById(id);
    return entityOptional.map(this.quotationFieldsMapper::toDto)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
  }

  @Override
  public QuotationResponseDto create(final QuotationRequestDto requestDto) {
    try {
      log.info("Creating quotation with data: {}", requestDto);
      final QuotationEntity entityMapped = this.quotationFieldsMapper.toEntity(requestDto);
      log.info("Mapped quotation entity: {}", entityMapped);
      final QuotationEntity entity = this.quotationRepository.save(entityMapped);
      return this.quotationFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException ex) {
      log.warn("Data integrity violation when creating quotation: {}", ex.getMessage());
      throw new QuotationException(QuotationMessage.QUOTATION_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    this.quotationRepository.findById(id)
      .orElseThrow(() -> new QuotationException(QuotationMessage.QUOTATION_NOT_FOUND));
    this.quotationRepository.deleteById(id);
  }
}
