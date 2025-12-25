package com.codesoft.quotations.quotation.mapper;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationDetailRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationDetailResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuotationDetailFieldsMapper {

  @Mapping(target = "quotation", ignore = true)
  @Mapping(target = "suggestedPrice", ignore = true)
  @Mapping(target = "totalLinePrice", ignore = true)
  @Mapping(target = "unitProductionCost", ignore = true)
  //Campos calculados se ignoran en el mapeo
  @Mapping(target = "overheadsAmount", ignore = true)
  @Mapping(target = "feeAmount", ignore = true)
  @Mapping(target = "rebateAmount", ignore = true)
  @Mapping(target = "overheadsPercentage", ignore = true)
  @Mapping(target = "feePercentage", ignore = true)
  @Mapping(target = "rebatePercentage", ignore = true)
  @Mapping(target = "profitMarginPercentage", ignore = true)
  QuotationDetailEntity toEntity(final QuotationDetailRequestDto source);

  @Mapping(target = "subItems.material", ignore = true)
  QuotationDetailResponseDto toDto(final QuotationDetailEntity destination);

  List<QuotationDetailResponseDto> toDtoList(final List<QuotationDetailEntity> entityList);
}
