package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	Page<Product> findAllBySeller_SellerNo(Long sellerNo, Pageable pageable);
	@Query(value = "SELECT * FROM PRODUCT WHERE seller_no = :sellerNo and "
			+ "product_no in (SELECT product_no "
			+ "from order_product "
			+ "where order_no not in ( "
			+ "select order_no "
			+ "from orders "
			+ "where import_no is null "
			+ ") "
			+ ") and current_stock < safety_stock", nativeQuery = true)
	Page<Product> findByNeededToOrder(@Param("sellerNo") Long sellerNo, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.seller.sellerNo = :sellerNo AND p.currentStock < p.safetyStock")
	List<Product> findByNeededToOrder(@Param("sellerNo") Long sellerNo);
	List<Product> findByProductName(String productName);
	
}
