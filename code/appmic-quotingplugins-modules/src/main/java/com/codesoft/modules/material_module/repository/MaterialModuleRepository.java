package com.codesoft.modules.material_module.repository;

import java.util.List;

import com.codesoft.modules.material_module.model.entity.MaterialModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialModuleRepository extends JpaRepository<MaterialModuleEntity, Integer> {

  /**
   * Find material modules by module name.
   *
   * @param moduleName the module name.
   * @return the list of material module entities.
   */
  List<MaterialModuleEntity> findByModuleName(final String moduleName);
}
