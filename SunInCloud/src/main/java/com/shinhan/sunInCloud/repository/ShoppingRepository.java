package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Shopping;

public interface ShoppingRepository extends JpaRepository<Shopping, String>{
	List<Shopping> findBySeller_SellerNoAndIsCollected(Long sellerNo, boolean isCollected);
}
