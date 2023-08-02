package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.OrderProduct;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.OrderProductRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final ProductService productService;
	private final SellerService sellerService;

	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
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

	/**
	 * 발주 등록 메서드
	 * 1. Seller를 찾고
	 * 2. Seller를 포함하여 발주를 저장하고
	 * 3. 발주가 필요한 상품들 조회하고
	 * 4. 저장된 발주를 기반으로 발주 상품들 저장하기
	 * @param sellerNo
	 * @return 저장이 필요한 물품의 개수와 저장된 물품의 개수가 동일하다면 true, 아니면 false
	 */
	public boolean register(Long sellerNo) {
		Seller seller = sellerService.findById(sellerNo);
		Order order = orderRepository.save(Order.builder().seller(seller).build());
		if (order.getOrderNo() == null) {
			return false;
		}
		List<Product> neededToOrderProducts = productService.findNeededToOrderBySellerNo(sellerNo);
		if (neededToOrderProducts.size() == 0) {
			return false;
		}
		List<OrderProduct> orderProducts = new ArrayList<>();
		for (Product neededToOrderProduct : neededToOrderProducts) {
			orderProducts.add(OrderProduct.builder()
					.order(order)
					.product(neededToOrderProduct)
					.amount(neededToOrderProduct.getEnoughStock() - neededToOrderProduct.getCurrentStock())
					.build());
		}
		List<OrderProduct> savedOrderProducts = orderProductRepository.saveAll(orderProducts);
		return savedOrderProducts.size() == orderProducts.size() ? true : false;
	}
}
