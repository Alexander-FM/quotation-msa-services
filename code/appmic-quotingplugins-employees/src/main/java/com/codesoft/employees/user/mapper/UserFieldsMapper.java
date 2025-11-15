package com.codesoft.employees.user.mapper;

import java.util.List;

import com.codesoft.employees.user.dto.request.UserRequestDto;
import com.codesoft.employees.user.dto.response.UserResponseDto;
import com.codesoft.employees.user.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserFieldsMapper {

  UserEntity toEntity(final UserRequestDto source);

  UserResponseDto toDto(final UserEntity destination);

  List<UserResponseDto> toDtoList(final List<UserEntity> entityList);
}
