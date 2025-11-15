package com.codesoft.employees.role.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.employees.role.dto.request.RoleRequestDto;
import com.codesoft.employees.role.dto.response.RoleResponseDto;
import com.codesoft.employees.role.mapper.RoleFieldsMapper;
import com.codesoft.employees.role.model.entity.RoleEntity;
import com.codesoft.employees.role.repository.RoleRepository;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  private final RoleFieldsMapper roleFieldsMapper;

  @Override
  public List<RoleResponseDto> findAll() {
    final List<RoleEntity> entities = this.roleRepository.findAll().stream().toList();
    return roleFieldsMapper.toDtoList(entities);
  }

  @Override
  public RoleResponseDto findById(final Integer id) {
    final Optional<RoleEntity> entity = this.roleRepository.findById(id);
    return entity.map(this.roleFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public RoleResponseDto create(final RoleRequestDto requestDto) {
    final RoleEntity entity = this.roleRepository.save(this.roleFieldsMapper.toEntity(requestDto));
    return this.roleFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(final Integer id) {
    final RoleEntity existingEntity = this.roleRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.roleRepository.deleteById(existingEntity.getId());
  }
}
