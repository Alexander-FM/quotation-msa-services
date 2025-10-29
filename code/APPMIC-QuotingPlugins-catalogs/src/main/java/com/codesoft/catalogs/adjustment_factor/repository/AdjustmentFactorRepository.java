package com.codesoft.catalogs.adjustment_factor.repository;

import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustmentFactorRepository extends CrudRepository<AdjustmentFactorEntity, Integer> {

}
