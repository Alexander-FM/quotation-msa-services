package com.codesoft.quotations.quotation.mapper;

import java.util.List;

import com.codesoft.quotations.quotation.dto.request.QuotationRequestDto;
import com.codesoft.quotations.quotation.dto.response.QuotationResponseDto;
import com.codesoft.quotations.quotation.entity.QuotationEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuotationFieldsMapper {

  @Mapping(target = "date", ignore = true)
  @Mapping(target = "state", ignore = true)
  @Mapping(target = "totalProductionCost", ignore = true)
  @Mapping(target = "totalFinalPrice", ignore = true)
  QuotationEntity toEntity(final QuotationRequestDto source);
  // En el DTO de respuesta, ignoramos 'material' porque lo llenaremos en el Service
  @Mapping(target = "customerName", ignore = true)
  @Mapping(target = "employeeName", ignore = true)
  QuotationResponseDto toDto(final QuotationEntity destination);

  List<QuotationResponseDto> toDtoList(final List<QuotationEntity> entityList);

  @AfterMapping
  default void linkParentReferences(@MappingTarget final QuotationEntity target) {
    if (target.getDetails() != null) {
      target.getDetails().forEach(detail -> {
        // Conecta Detalle -> Cabecera
        detail.setQuotation(target);
        if (detail.getSubItems() != null) {
          // Conecta SubItem -> Detalle
          detail.getSubItems().forEach(subItem -> subItem.setQuotationDetail(detail));
        }
      });
    }
  }
}
