package com.shinhan.sunInCloud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	Page<Product> findAllBySeller_SellerNo(Long sellerNo, Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.seller.sellerNo = :sellerNo AND p.currentStock < p.safetyStock")
	Page<Product> findByNeededToOrder(@Param("sellerNo") Long sellerNo, Pageable pageable);
}
