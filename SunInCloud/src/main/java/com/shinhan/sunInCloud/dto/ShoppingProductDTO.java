package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.entity.ShoppingProduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingProductDTO {
	private String exportNo;

	private String address;

	private String ordererName;

	private String productName;

	private String productNo;

	private int amount;

	private int sellingPrice;

	private Timestamp orderDate;

	private String orderStatus;

	private String invoiceNo;

	/**
	 * ShoppingProductDTP -> Shopping
	 * 요청을 주문 목록으로 만드는 메서드
	 * 
	 * @param exportNo
	 * @param salesChannel
	 * @param seller
	 * @return
	 */
	public Shopping toShopping(String exportNo, String salesChannel, Seller seller) {
		return Shopping
				.builder()
				.address(address)
				.exportNo(exportNo)
				.orderName(ordererName)
				.salesChannel(salesChannel)
				.seller(seller)
				.build();
	}
	
	/**
	 * ShoppingProductDTO -> ShoppingProduct
	 * 요청을 주문 상품으로 만드는 메서드
	 * 
	 * @param product
	 * @param shopping
	 * @return
	 */
	public ShoppingProduct toShoppingProduct(Product product, Shopping shopping) {
		return ShoppingProduct
				.builder()
				.amount(amount)
				.product(product)
				.sellingPrice(sellingPrice)
				.shopping(shopping)
				.build();
	}
}
