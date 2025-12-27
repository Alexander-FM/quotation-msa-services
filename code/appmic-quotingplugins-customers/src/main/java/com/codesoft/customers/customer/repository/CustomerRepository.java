package com.codesoft.customers.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.codesoft.customers.customer.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

  Optional<CustomerEntity> findByDocumentNumber(final String documentNumber);

  /**
   * Busca todos los clientes cuyos números de documento estén en la lista proporcionada.
   *
   * @param documentNumberList lista de números de documento a buscar
   * @return lista de empleados encontrados
   */
  @Query("SELECT c FROM CustomerEntity c WHERE c.documentNumber IN :documentNumberList")
  List<CustomerEntity> findAllByDocumentNumber(final Set<String> documentNumberList);
}
