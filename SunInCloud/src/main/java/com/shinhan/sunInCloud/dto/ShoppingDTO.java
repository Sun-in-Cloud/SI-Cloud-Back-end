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
	
	/**
	 * ShoppingDTO -> Exports
	 * 주문건을 출고 목록으로 등록하기 위함
	 * @param seller
	 * @return
	 */
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
