package com.codesoft.employees.employee.repository;

import java.util.Optional;

import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

  Optional<EmployeeEntity> findByDocumentNumber(final String documentNumber);

  /**
   * Busca si el userId ya está asociado a otro empleado distinto al Id proporcionando.
   * @param userId id del usuario a buscar
   * @param id id del empleado a excluir de la búsqueda
   * @return true si existe otro empleado con el mismo userId, false en caso contrario
   */
  boolean existsByUserIdAndIdNot(final Integer userId, Integer id);
}
