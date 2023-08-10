package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.dto.ChannelSalesDTO;
import com.shinhan.sunInCloud.dto.DangerousProductDTO;
import com.shinhan.sunInCloud.dto.ProductSalesDTO;
import com.shinhan.sunInCloud.entity.Exports;

public interface ExportsRepository extends JpaRepository<Exports, Long>{
	Page<Exports> findAllBySeller_SellerNoOrderByOrderDateDesc(Long sellerNo, Pageable pageable);

	@Query("select new com.shinhan.sunInCloud.dto.ChannelSalesDTO(e.salesChannel, sum(ep.sellingPrice) as totalSales) "
			+ "from Exports e inner join ExportProduct ep on e.exportNo = ep.exports.exportNo "
			+ "where e.seller.sellerNo = :sellerNo and ep.exportDate is not null and year(ep.exportDate) = :year "
			+ "group by e.salesChannel "
			+ "order by totalSales desc ")
	List<ChannelSalesDTO> findTopChannels(@Param("sellerNo") Long sellerNo, @Param("year") int year, Pageable pageable);
	
	@Query("select new com.shinhan.sunInCloud.dto.ProductSalesDTO(p.productName, sum(ep.sellingPrice) as totalSales, p.detailProductGroup.groupName)"
			+ "from Product p inner join ExportProduct ep on p.productNo = ep.product.productNo inner join Exports e on e.exportNo = ep.exports.exportNo "
			+ "where e.seller.sellerNo = :sellerNo and ep.exportDate is not null and e.salesChannel = :channelName and year(ep.exportDate) = :year "
			+ "group by p.productName "
			+ "order by totalSales desc")
	List<ProductSalesDTO> findTopProductsOfChannel(@Param("sellerNo") Long sellerNo, @Param("year") int year, @Param("channelName") String channelName, Pageable pageable);
	
	@Query("select new com.shinhan.sunInCloud.dto.DangerousProductDTO(p.productName, p.productNo, p.currentStock, p.importPrice, p.consumerPrice, max(o.orderDate) AS orderDate) "
			+ "from Product p join OrderProduct op on p.productNo = op.product.productNo join Order o on op.order.orderNo = o.orderNo "
			+ "where p.seller.sellerNo = :sellerNo "
			+ "group by p.productNo "
			+ "order by max(o.orderDate)")
	List<DangerousProductDTO> getDangerousProducts(@Param("sellerNo") Long sellerNo, Pageable pageable);
}
