package com.codesoft.role.model.entity;

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
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "role_name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_gen")
  @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_seq", allocationSize = 1)
  private Integer id;

  @Column(name = "role_name", length = 100, nullable = false, unique = true)
  private String roleName;

  @Column(name = "description")
  private String description;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;
}
