package com.shinhan.sunInCloud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.ThreePL;

public interface ThreePLRepository extends JpaRepository<ThreePL, Long>{
	@Query(value = "SELECT * "
			+ "FROM threepl "
			+ "	JOIN product_group "
			+ "	ON threepl.product_group_no = product_group.product_group_no "
			+ "	JOIN (SELECT threepl_no, IFNULL(MIN(end_date), SYSDATE()) AS end_date "
			+ "		FROM matching "
			+ "		RIGHT OUTER JOIN warehouse "
			+ "		ON matching.warehouse_no = warehouse.warehouse_no "
			+ "		GROUP BY warehouse.threepl_no) AS dateinfo "
			+ "	ON threepl.threepl_no = dateinfo.threepl_no "
			+ "WHERE product_group.group_name LIKE CONCAT('%', :productGroup, '%')	 "
			+ "AND threepl.address LIKE CONCAT('%', :address, '%') "
			+ "AND threepl.fee <= :fee "
			+ "AND (end_date <= DATE_ADD(SYSDATE(), INTERVAL :contractPeriod MONTH) OR (cnt_total - cnt_contracted > 0)) "
			+ "ORDER BY (cnt_total - cnt_contracted) DESC, end_date, company_name", nativeQuery = true)
	public Page<ThreePL> findByMatchingCondition(@Param("productGroup") String productGroup, @Param("address") String address,
			@Param("fee") long fee, @Param("contractPeriod") int contractPeriod, Pageable pageable);
	
	public Page<ThreePL> findAllByOrderByCompanyName(Pageable pageable);
	public ThreePL findByBusinessNo(String businessNo);
}
