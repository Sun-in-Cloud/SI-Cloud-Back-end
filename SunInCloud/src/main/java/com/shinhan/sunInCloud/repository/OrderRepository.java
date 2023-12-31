package com.shinhan.sunInCloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findBySeller_SellerNoOrderByOrderDateDesc(Long sellerNo, Pageable pageable);
	List<Order> findBySeller_SellerNo(Long sellerNo);
	Long countBySeller_SellerNo(Long sellerNo);
	//진경
	Optional<Order> findById(Long orderNo);
	Page<Order> findBySeller_SellerNoAndImportsIsNull(Long sellerNo, Pageable pageable);
	Long countBySeller_SellerNoAndImportsIsNull(Long sellerNo);
}
