package com.codesoft.quotations.modules.module_material.repository;

import com.codesoft.quotations.modules.module_material.entity.ModuleMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleMaterialRepository extends JpaRepository<ModuleMaterialEntity, Integer> {

}
