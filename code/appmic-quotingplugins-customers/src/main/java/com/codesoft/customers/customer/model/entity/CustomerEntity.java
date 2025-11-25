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
    uniqueConstraints = @UniqueConstraint(name = "customers_document_number", columnNames = "document_number")
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
  @SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq", allocationSize = 1)
  private Integer id;

  @Column(name = "fullname", nullable = false)
  private String fullName;

  @Column(name = "document_type_code", nullable = false, length = 20)
  private String documentTypeCode;

  @Column(name = "document_number", nullable = false, length = 20, unique = true)
  private String documentNumber;

  @Column(name = "phone_number", nullable = false, length = 9)
  private String phoneNumber;

  @Column(name = "phone_number2", length = 9)
  private String phoneNumber2;

  @Column(name = "email")
  private String email;

  @Column(name = "street_address", nullable = false)
  private String streetAddress;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
