package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;
import java.util.List;

import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingDTO {
	private Long sellerNo;
	private String exportNo;
	private String address;
	private String ordererName;
	private String salesChannel;
	private Timestamp orderDate;
	private List<ShoppingProductDTO> orderedProducts;
	
	public Exports toExports(Seller seller) {
		return Exports
				.builder()
				.address(address)
				.exportNo(exportNo)
				.orderDate(orderDate)
				.ordererName(ordererName)
				.salesChannel(salesChannel)
				.seller(seller)
				.build();
	}
}
