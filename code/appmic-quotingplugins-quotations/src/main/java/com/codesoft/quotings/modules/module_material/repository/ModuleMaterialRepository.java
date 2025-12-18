package com.codesoft.quotings.modules.module_material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleMaterialRepository extends JpaRepository<ModuleMaterialEntity, Integer> {

}
