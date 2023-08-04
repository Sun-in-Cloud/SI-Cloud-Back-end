package com.shinhan.sunInCloud.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.Exports;

public interface ExportsRepository extends JpaRepository<Exports, Long>{
	Page<Exports> findAllBySeller_SellerNoOrderByOrderDateDesc(Long sellerNo, Pageable pageable);
	
	@Query("SELECT e.orderDate, COUNT(e) "
			+ "FROM Exports e "
			+ "WHERE e.orderDate BETWEEN :startDate AND :endDate "
			+ "GROUP BY e.orderDate")
	List<Object[]> getDailySalesCountForWeek(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
