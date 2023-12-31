package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	Page<Product> findAllBySeller_SellerNoAndIsActiveOrderByProductName(Long sellerNo, Pageable pageable, Boolean isActive);
	@Query(value = "SELECT * FROM PRODUCT WHERE seller_no = :sellerNo and "
			+ "product_no not in (SELECT product_no "
			+ "from order_product "
			+ "where order_no in ( "
			+ "select order_no "
			+ "from orders "
			+ "where import_no is null "
			+ ") "
			+ ") and is_active = 1 and current_stock < safety_stock", nativeQuery = true)
	Page<Product> findByNeededToOrder(@Param("sellerNo") Long sellerNo, Pageable pageable);
	
	@Query(value = "SELECT count(*) FROM PRODUCT WHERE seller_no = :sellerNo and "
			+ "product_no not in (SELECT product_no "
			+ "from order_product "
			+ "where order_no in ( "
			+ "select order_no "
			+ "from orders "
			+ "where import_no is null "
			+ ") "
			+ ") and is_active = 1 and current_stock < safety_stock", nativeQuery = true)
	Long countNeededToOrder(@Param("sellerNo") Long sellerNo);
	
	@Query(value = "SELECT * FROM PRODUCT WHERE seller_no = :sellerNo and "
			+ "product_no not in (SELECT product_no "
			+ "from order_product "
			+ "where order_no in ( "
			+ "select order_no "
			+ "from orders "
			+ "where import_no is null "
			+ ") "
			+ ") and is_active = 1 and current_stock < safety_stock", nativeQuery = true)
	List<Product> findByNeededToOrder(@Param("sellerNo") Long sellerNo);
	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:productName% AND p.seller.sellerNo = :sellerNo")
	List<Product> findByProductNameContainingAndSellerNo(@Param("productName") String productName, @Param("sellerNo") Long sellerNo);
	Long countBySeller_SellerNo(Long sellerNo);
	boolean existsByProductNameAndSeller_SellerNo(String productName, Long sellerNo);
	
//	@Query("select p from Product as p "
//			+ "where p.sellerNo = :sellerNo")
//	List<SimpleProductDTO> findByAllProductSimpledata(@Param("sellerNo") Long sellerNo);
	
	@Query(value = "SELECT product_no, consumer_price "
			+ "FROM product "
			+ "WHERE seller_no = :sellerNo", nativeQuery = true)
	List<Object[]> findByAllProductSimpledata(@Param("sellerNo") Long sellerNo);
}
