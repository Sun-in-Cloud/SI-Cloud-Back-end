package com.shinhan.sunInCloud.dto;

import com.shinhan.sunInCloud.entity.DetailProductGroup;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductDTO {

	private Long sellerNo;
	private String productNo;
	private String productGroup;
	private String productName;
	private int safetyStock;
	private int currentStock;
	private int enoughStock;
	private int importPrice;
	private int consumerPrice;
	
	/**
	 * ProductDTO -> Product
	 * @return product
	 * 작성자: 손준범
	 */
	public Product toProduct(Seller seller, DetailProductGroup detailProductGroup) {
		return Product.builder()
				.seller(seller)
				.productName(productName)
				.safetyStock(safetyStock)
				.enoughStock(enoughStock)
				.detailProductGroup(detailProductGroup)
				.importPrice(importPrice)
				.consumerPrice(consumerPrice)
				.isActive(true)
				.build();
	}
}