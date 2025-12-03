package com.codesoft.catalogs.unit_of_measurement.repository;

import java.util.Optional;

import com.codesoft.catalogs.unit_of_measurement.model.entity.UnitOfMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurementEntity, Integer> {

  /**
   * Find the unit of measurement by name.
   *
   * @param name the name.
   * @return the optional catalog item entity.
   */
  Optional<UnitOfMeasurementEntity> findByNameContainingIgnoreCase(final String name);
}
