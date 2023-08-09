package com.shinhan.sunInCloud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class ProductSalesDTO {

	private String productName;
	private Long totalSales;
	private String productGroup;
	
	public ProductSalesDTO(String productName, Long totalSales, String productGroup) {
		this.productName = productName;
		this.totalSales = totalSales;
		this.productGroup = productGroup;
	}
}
