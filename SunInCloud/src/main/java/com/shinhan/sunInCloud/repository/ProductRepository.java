package com.shinhan.sunInCloud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	Page<Product> findAllBySeller_SellerNo(Long sellerNo, Pageable pageable);
}
