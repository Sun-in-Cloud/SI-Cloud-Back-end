package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

	private Long orderNo;
	private Timestamp orderDateTimeStamp;
	private String orderDate;
	private Long importNo;
	private Boolean isImported;
	
	public Order toOrder(Seller seller, Imports imports) {
		return Order.builder()
				.orderNo(orderNo)
				.orderDate(orderDateTimeStamp)
				.imports(imports)
				.build();
	}
}
