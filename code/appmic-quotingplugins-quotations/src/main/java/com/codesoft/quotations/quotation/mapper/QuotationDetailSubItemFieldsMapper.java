package com.codesoft.quotations.quotation.mapper;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationDetailSubItemRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationDetailSubItemResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationDetailSubItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuotationDetailSubItemFieldsMapper {

  @Mapping(target = "unitPrice", ignore = true)
  @Mapping(target = "totalPrice", ignore = true)
  @Mapping(target = "quotationDetail", ignore = true)
  QuotationDetailSubItemEntity toEntity(final QuotationDetailSubItemRequestDto source);

  // El campo material se rellena en el servicio, por eso se ignora aquí
  @Mapping(target = "material", ignore = true)
  QuotationDetailSubItemResponseDto toDto(final QuotationDetailSubItemEntity destination);

  List<QuotationDetailSubItemResponseDto> toDtoList(final List<QuotationDetailSubItemEntity> entityList);
}
