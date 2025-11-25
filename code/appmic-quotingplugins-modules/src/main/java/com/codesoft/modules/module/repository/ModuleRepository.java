package com.codesoft.modules.module.repository;

import java.util.Optional;

import com.codesoft.modules.module.model.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {

  /**
   * Find module by name and is active true.
   *
   * @param name the module name.
   * @return the optional module entity.
   */
  Optional<ModuleEntity> findByNameAndIsActiveTrue(final String name);
}
