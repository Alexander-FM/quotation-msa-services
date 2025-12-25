package com.codesoft.quotations.quotation.repository;

import com.codesoft.quotations.quotation.entity.QuotationDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationDetailRepository extends JpaRepository<QuotationDetailEntity, Integer> {

}
