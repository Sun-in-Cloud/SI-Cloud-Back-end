package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, Long>{
	
	public Seller findByBusinessNo(String businessNo);
	
	@Query(value = "SELECT seller.seller_no, company_name, group_name, end_date "
			+ "FROM seller "
			+ "	JOIN product_group "
			+ "	ON seller.product_group_no = product_group.product_group_no "
			+ "	LEFT OUTER JOIN matching "
			+ "	ON seller.seller_no = matching.seller_no "
			+ "WHERE seller.seller_no IN (SELECT seller.seller_no "
			+ "	FROM exports "
			+ "		JOIN export_product "
			+ "		ON exports.export_no = export_product.export_no "
			+ "		RIGHT OUTER JOIN seller "
			+ "		ON exports.seller_seller_no = seller.seller_no "
			+ " 	WHERE IFNULL(order_status, '') LIKE if(:exportCnt > 0, '출고완료', '%') "
			+ "	GROUP BY seller.seller_no "
			+ "	HAVING COUNT(*) >= :exportCnt) "
			+ "AND product_group.group_name LIKE '%:groupName%' "
			+ "AND seller.address LIKE '%:address%' "
			+ "AND IFNULL(end_date, SYSDATE()) <= DATE_ADD(SYSDATE(), INTERVAL :contractPeriod MONTH)", nativeQuery = true)
	public List<Seller> findByMatchingCondition(@Param("groupName")String groupName, @Param("address")String address, 
			@Param("exportCnt")int exportCnt, @Param("contractPeriod")int contractPeriod);
}
