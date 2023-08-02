package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.entity.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final ProductService productService;

	/**
	 * 발주가 필요한 상품의 목록을 조회하는 메서드
	 * 1. ProductService에서 발주가 필요한 상품을 조회하고
	 * 2. DTO로 Convert
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return 발주가 필요한 상품 List
	 * 작성자: 손준범
	 */
	public List<OrderProductDTO> findNeededOrderProducts(Long sellerNo, int pageNum, int countPerPage) {
		Page<Product> neededToOrderProducts = productService.findNeededToOrderBySellerNo(sellerNo, PageRequest.of(pageNum, countPerPage));
		List<OrderProductDTO> products = new ArrayList<>();
		for (Product product : neededToOrderProducts) {
			products.add(OrderProductDTO.builder()
					.productNo(product.getProductNo())
					.productName(product.getProductName())
					.safetyStock(product.getSafetyStock())
					.currentStock(product.getCurrentStock())
					.enoughStock(product.getEnoughStock())
					.amount(product.getEnoughStock() - product.getCurrentStock())
					.build());
		}
		return products;
	}
}
