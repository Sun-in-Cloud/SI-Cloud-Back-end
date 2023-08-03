package com.shinhan.sunInCloud.dto;

import com.shinhan.sunInCloud.entity.OrderProduct;
import com.shinhan.sunInCloud.entity.Product;

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
	
	//바코드 번호, 발주 수량, 상품명
	public OrderProduct toOrderProductDTO(Product product) {
		return OrderProduct.builder()
				.product(product)
				.amount(amount)
				.build();
	}
}
