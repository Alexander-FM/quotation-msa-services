package com.codesoft.customers.customer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "customers",
    uniqueConstraints = {
        @UniqueConstraint(name = "customers_company_name", columnNames = "company_name"),
        @UniqueConstraint(name = "customers_document_number", columnNames = "document_number")
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_seq")
  @SequenceGenerator(name = "customers_id_seq", sequenceName = "customers_id_seq", allocationSize = 1)
  private Integer id;

  @Column(name = "company_name", nullable = false, unique = true)
  private String companyName;

  @Column(name = "document_type_code", nullable = false, length = 20)
  private String documentTypeCode;

  @Column(name = "document_number", nullable = false, length = 20, unique = true)
  private String documentNumber;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "phonenumber", nullable = false, length = 20)
  private String phoneNumber;

  @Column(name = "phonenumber2", length = 20)
  private String phoneNumber2;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
