package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findBySeller_SellerNoOrderByOrderDateDesc(Long sellerNo, Pageable pageable);
	List<Order> findBySeller_SellerNo(Long sellerNo);
	Long countBySeller_SellerNo(Long sellerNo);
}
