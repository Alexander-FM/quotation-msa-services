package com.codesoft.employees.employee.repository;

import java.util.Optional;

import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

  Optional<EmployeeEntity> findByDocumentNumber(final String documentNumber);

  boolean existsByUserId(final Integer userId);
}
