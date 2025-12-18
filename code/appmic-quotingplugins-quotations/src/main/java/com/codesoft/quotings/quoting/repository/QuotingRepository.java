package com.codesoft.quotings.quoting.repository;

import com.codesoft.quotings.quoting.entity.QuotingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotingRepository extends JpaRepository<QuotingEntity, Integer> {

}
