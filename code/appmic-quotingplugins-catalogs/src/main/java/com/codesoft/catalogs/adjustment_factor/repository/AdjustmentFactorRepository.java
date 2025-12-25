package com.codesoft.catalogs.adjustment_factor.repository;

import java.util.Optional;

import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustmentFactorRepository extends JpaRepository<AdjustmentFactorEntity, Integer> {

  /**
   * Find the unit of adjustment factor by name.
   *
   * @param name the name.
   * @return the optional catalog item entity.
   */
  Optional<AdjustmentFactorEntity> findByName(final String name);
}
