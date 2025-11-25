package com.codesoft.modules.module_concepts.repository;

import java.util.Optional;

import com.codesoft.modules.module_concepts.model.entity.ModuleConceptsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleConceptsRepository extends JpaRepository<ModuleConceptsEntity, Integer> {

  /**
   * Find module concepts by module name.
   *
   * @param moduleName the module name.
   * @return the optional module concepts entity.
   */
  Optional<ModuleConceptsEntity> findByModuleName(final String moduleName);
}
