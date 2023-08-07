package com.shinhan.sunInCloud.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.ExportProduct;

public interface ExportProductRepository extends JpaRepository<ExportProduct, Long>{
	Long countByExports_ExportNoAndOrderStatus(String exportNo, String orderStatus);
	Page<ExportProduct> findByExports_ExportNoOrderByProduct_ProductName(String exportNo, Pageable pageable);
	ExportProduct findByExports_ExportNoAndProduct_ProductNo(String exportNo, String productNo);
	
	@Query(value = "SELECT Date(e.export_date) as exportDate, COUNT(DISTINCT export_no) as exportCount "
			+ "FROM export_product e "
			+ "WHERE export_no in ( "
			+ "SELECT export_no "
			+ "FROM exports "
			+ "WHERE seller_seller_no = :sellerNo ) "
			+ " AND "
			+ "export_date IS NOT NULL AND DATE_FORMAT(e.export_date, '%Y-%m-%d') IN (:dates) "
			+ "GROUP BY Date(e.export_date)", nativeQuery = true)
	List<Object[]> getDailySalesCountForWeek(@Param("dates") List<String> dates, @Param("sellerNo") Long sellerNo);
	
	@Query(value = "SELECT COUNT(DISTINCT export_no) as exportCount "
			+ "FROM export_product "
			+ "WHERE export_no in ( "
			+ "SELECT export_no "
			+ "FROM exports "
			+ "WHERE seller_seller_no = :sellerNo ) AND "
			+ "YEAR(export_date) = :year AND MONTH(export_date) =:month", nativeQuery = true)
	Long getSalesCountOfMonth(@Param("sellerNo") Long sellerNo, @Param("year") int year, @Param("month") int month);
	
	@Query(value = "SELECT COUNT(DISTINCT export_no) as exportCount "
			+ "FROM export_product "
			+ "WHERE export_no in ( "
			+ "SELECT export_no "
			+ "FROM exports "
			+ "WHERE seller_seller_no = :sellerNo ) AND "
			+ "YEAR(export_date) = :year", nativeQuery = true)
	Long getSalesCountOfYear(@Param("sellerNo") Long sellerNo, @Param("year") int year);
	
	@Query(value = "SELECT Date(e.export_date) as exportDate, SUM(selling_price) as totalSales "
			+ "FROM export_product e "
			+ "WHERE export_no in ( "
			+ "SELECT export_no "
			+ "FROM exports "
			+ "WHERE seller_seller_no = :sellerNo ) "
			+ " AND "
			+ "export_date IS NOT NULL AND Date(e.export_date) IN (:dates) "
			+ "GROUP BY Date(e.export_date)", nativeQuery = true)
	List<Object[]> getDailySalesForWeek(@Param("dates") List<String> dates, @Param("sellerNo") Long sellerNo);
	
	@Query(value = "SELECT COALESCE(SUM(selling_price), 0) as totalSales "
			+ "FROM export_product "
			+ "WHERE export_no in ( "
			+ "SELECT export_no "
			+ "FROM exports "
			+ "WHERE seller_seller_no = :sellerNo ) AND "
			+ "YEAR(export_date) = :year AND MONTH(export_date) =:month", nativeQuery = true)
	Long getMonthlySales(@Param("sellerNo") Long sellerNo, @Param("year") int year, @Param("month") int month);
	
	@Query(value = "SELECT COALESCE(SUM(selling_price), 0) as totalSales "
			+ "FROM export_product "
			+ "WHERE export_no in ( "
			+ "SELECT export_no "
			+ "FROM exports "
			+ "WHERE seller_seller_no = :sellerNo ) AND "
			+ "YEAR(export_date) = :year", nativeQuery = true)
	Long getYearlySales(@Param("sellerNo") Long sellerNo, @Param("year") int year);
}
