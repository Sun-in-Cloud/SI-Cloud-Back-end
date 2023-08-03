package com.shinhan.sunInCloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findBySeller_SellerNoOrderByOrderDateDesc(Long sellerNo, Pageable pageable);
	Optional<Order> findBySellerNo(String sellerNo);
	
	
}
