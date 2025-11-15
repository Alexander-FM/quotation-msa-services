package com.codesoft.employees.user.repository;

import java.util.Optional;

import com.codesoft.employees.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUsername(final String username);
}
