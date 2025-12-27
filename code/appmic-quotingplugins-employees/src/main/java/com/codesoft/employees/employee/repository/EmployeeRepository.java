package com.codesoft.employees.employee.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

  Optional<EmployeeEntity> findByDocumentNumber(final String documentNumber);

  /**
   * Busca si el userId ya está asociado a otro empleado distinto al Id proporcionando.
   *
   * @param userId id del usuario a buscar
   * @param id id del empleado a excluir de la búsqueda
   * @return true si existe otro empleado con el mismo userId, false en caso contrario
   */
  boolean existsByUserIdAndIdNot(final Integer userId, Integer id);

  /**
   * Busca todos los empleados cuyos números de documento estén en la lista proporcionada.
   *
   * @param documentNumberList lista de números de documento a buscar
   * @return lista de empleados encontrados
   */
  @Query("SELECT e FROM EmployeeEntity e WHERE e.documentNumber IN :documentNumberList")
  List<EmployeeEntity> findAllByDocumentNumber(final Set<String> documentNumberList);
}
