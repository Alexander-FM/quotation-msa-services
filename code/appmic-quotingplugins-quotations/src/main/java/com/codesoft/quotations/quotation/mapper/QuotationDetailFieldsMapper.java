package com.codesoft.quotations.quotation.mapper;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationDetailRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationDetailResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationDetailFieldsMapper {

  QuotationDetailEntity toEntity(final QuotationDetailRequestDto source);

  QuotationDetailResponseDto toDto(final QuotationDetailEntity destination);

  List<QuotationDetailResponseDto> toDtoList(final List<QuotationDetailEntity> entityList);
}
