package com.codesoft.quotings.quoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotingRepository extends JpaRepository<ModuleMaterialEntity, Integer> {

}
