package com.codesoft.quotings.quoting.mapper;

import java.util.List;

import com.codesoft.quotings.quoting.dto.request.QuotingRequestDto;
import com.codesoft.quotings.quoting.dto.response.QuotingResponseDto;
import com.codesoft.quotings.quoting.entity.QuotingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotingFieldsMapper {

  QuotingEntity toEntity(final QuotingRequestDto source);

  QuotingResponseDto toDto(final QuotingEntity destination);

  List<QuotingResponseDto> toDtoList(final List<QuotingEntity> entityList);
}
