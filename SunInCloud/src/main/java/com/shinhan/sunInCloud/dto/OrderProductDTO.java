package com.shinhan.sunInCloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderProductDTO {

	private String productNo;
	private String productName;
	private Integer safetyStock;
	private Integer currentStock;
	private Integer enoughStock;
	private Integer amount;
}