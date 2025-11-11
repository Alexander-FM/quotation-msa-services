package com.codesoft.employees.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.codesoft.employees.role.dto.request.RoleRequestDto;
import com.codesoft.employees.role.mapper.RoleFieldsMapper;
import com.codesoft.employees.role.model.entity.RoleEntity;
import com.codesoft.employees.role.repository.RoleRepository;
import com.codesoft.employees.user.dto.request.UserRequestDto;
import com.codesoft.employees.user.dto.response.UserResponseDto;
import com.codesoft.employees.user.mapper.UserFieldsMapper;
import com.codesoft.employees.user.repository.UserRepository;
import com.codesoft.exception.BaseException;
import com.codesoft.employees.user.model.entity.UserEntity;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final UserFieldsMapper userFieldsMapper;

  private final RoleFieldsMapper roleFieldsMapper;

  @Override
  public List<UserResponseDto> findAll() {
    final List<UserEntity> entities = this.userRepository.findAll().stream().toList();
    return userFieldsMapper.toDtoList(entities);
  }

  @Override
  public UserResponseDto findById(final Integer id) {
    final Optional<UserEntity> entity = this.userRepository.findById(id);
    return entity.map(this.userFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public UserResponseDto create(final UserRequestDto requestDto) {
    Set<RoleEntity> roles =
        new HashSet<>(this.roleRepository.findAllById(requestDto.getRoles().stream().map(RoleRequestDto::getId).toList()));
    requestDto.setRoles(this.roleFieldsMapper.toRequestDtoSet(roles));
    final UserEntity entity = this.userRepository.save(this.userFieldsMapper.toEntity(requestDto));
    return this.userFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(final Integer id) {
    final UserEntity existingEntity = this.userRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.userRepository.deleteById(existingEntity.getId());
  }
}
