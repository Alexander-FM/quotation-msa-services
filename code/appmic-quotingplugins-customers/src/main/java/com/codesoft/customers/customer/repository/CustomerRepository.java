package com.codesoft.customers.customer.repository;

import java.util.Optional;

import com.codesoft.customers.customer.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

  Optional<CustomerEntity> findByDocumentNumber(final String documentNumber);
}
