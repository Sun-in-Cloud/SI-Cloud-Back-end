package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Matching;

public interface MatchingRepository extends JpaRepository<Matching, Long>{
	public List<Matching> findByWarehouse_ThreePL_ThreePLNo(Long threePLNo);
	public Matching findBySeller_SellerNo(Long sellerNo);
}
