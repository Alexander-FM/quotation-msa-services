package com.codesoft.quotings.modules.module.repository;

import com.codesoft.quotings.modules.module.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {

}
