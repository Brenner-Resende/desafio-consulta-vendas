package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT s FROM Sale s " +
	           "WHERE s.date BETWEEN :minDate AND :maxDate " +
	           "AND LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :sellerName, '%'))")
	    Page<Sale> report(@Param("minDate") LocalDate minDate,
	                         @Param("maxDate") LocalDate maxDate,
	                         @Param("sellerName") String sellerName,
	                         Pageable pageable);
	@Query("SELECT NEW com.devsuperior.dsmeta.dto.SalesSummaryDTO(s.seller.name, SUM(s.amount)) " +
	           "FROM Sale s " +
	           "WHERE s.date BETWEEN :minDate AND :maxDate " +
	           "GROUP BY s.seller.name")
	    List<SalesSummaryDTO> summary(
	            @Param("minDate") LocalDate minDate,
	            @Param("maxDate") LocalDate maxDate);
}
