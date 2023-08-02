package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.ShoppingProduct;

public interface ShoppingProductRepository extends JpaRepository<ShoppingProduct, Long>{
	List<ShoppingProduct> findByShopping_ExportNo(String exportNo);
}
