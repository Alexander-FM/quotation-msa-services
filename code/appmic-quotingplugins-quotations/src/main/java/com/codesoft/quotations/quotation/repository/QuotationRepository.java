package com.codesoft.quotations.quotation.repository;

import com.codesoft.quotations.quotation.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, Integer> {

}
