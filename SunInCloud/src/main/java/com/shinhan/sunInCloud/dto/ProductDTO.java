package com.shinhan.sunInCloud.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductDTO {

	private String productNo;
	private String productGroupName;
	private String productName;
	private int safetyStock;
	private int currentStock;
	private int enoughStock;
}
