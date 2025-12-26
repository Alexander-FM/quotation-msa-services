package com.codesoft.employees.employee.model.entity;

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
  name = "employees",
  uniqueConstraints = {
    @UniqueConstraint(name = "employees_document_number", columnNames = "document_number"),
    @UniqueConstraint(name = "employees_user_id", columnNames = "user_id"),
    @UniqueConstraint(name = "employees_phone_number", columnNames = "phone_number"),
    @UniqueConstraint(name = "employees_phone_number2", columnNames = "phone_number2")
  }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_seq")
  @SequenceGenerator(name = "employees_seq", sequenceName = "employees_seq", allocationSize = 1)
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

  @Column(name = "street_address", nullable = false)
  private String streetAddress;

  @Column(name = "user_id", nullable = false)
  private Integer userId;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;
}
