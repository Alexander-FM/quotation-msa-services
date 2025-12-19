package com.codesoft.quotations.quotation.mapper;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationDetailSubItemRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationDetailSubItemResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationDetailSubItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotationDetailSubItemFieldsMapper {

  QuotationDetailSubItemResponseDto toEntity(final QuotationDetailSubItemRequestDto source);

  QuotationDetailSubItemResponseDto toDto(final QuotationDetailSubItemEntity destination);

  List<QuotationDetailSubItemResponseDto> toDtoList(final List<QuotationDetailSubItemEntity> entityList);
}
