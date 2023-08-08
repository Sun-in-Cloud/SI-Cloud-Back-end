package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.Matching;

public interface MatchingRepository extends JpaRepository<Matching, Long>{
	public List<Matching> findByWarehouse_ThreePL_ThreePLNo(Long threePLNo);
	public Matching findBySeller_SellerNo(Long sellerNo);
	
	@Query(value = "SELECT seller_no FROM matching\r\n"
			+ "WHERE warehouse_no IN (SELECT warehouse_no FROM warehouse\r\n"
			+ "	WHERE threepl_no = :threePLNo);", nativeQuery = true)
	public List<Matching> findByThreePL(@Param("threePLNo") Long threePLNo);
}
