package com.codesoft.catalogs.adjustment_factor.repository;

import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustmentFactorRepository extends JpaRepository<AdjustmentFactorEntity, Integer> {

}
