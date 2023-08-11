package com.shinhan.sunInCloud.dto;

import java.util.Date;

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
public class DangerousProductDTO {

	private String productName;
	private String productNo;
	private Integer currentStock;
	private Integer importPrice;
	private Integer consumerPrice;
	private Date orderDate;
	private String lastOrderDate;
	
	public DangerousProductDTO(String productName, String productNo, Integer currentStock, Integer importPrice,
			Integer consumerPrice, Date orderDate) {
		this.productName = productName;
		this.productNo = productNo;
		this.currentStock = currentStock;
		this.importPrice = importPrice;
		this.consumerPrice = consumerPrice;
		this.orderDate = orderDate;
	}
}
