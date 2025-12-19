package com.codesoft.quotations.quotation.mapper;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationFieldsMapper {

  QuotationEntity toEntity(final QuotationRequestDto source);

  QuotationResponseDto toDto(final QuotationEntity destination);

  List<QuotationResponseDto> toDtoList(final List<QuotationEntity> entityList);
}
