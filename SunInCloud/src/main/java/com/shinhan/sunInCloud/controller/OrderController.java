package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.OrderDTO;
import com.shinhan.sunInCloud.dto.OrderListDTO;
import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.dto.OrderProductListDTO;
import com.shinhan.sunInCloud.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API Document")
public class OrderController {

	private final OrderService orderService;
	
	@GetMapping("/3pl/order/auto-list")
	@Operation(summary = "find products to be ordered", description = "find products of a seller whose stock is under the safety stock")
	public OrderProductListDTO findNeededOrderProducts(Long sellerNo, int pageNum, int countPerPage) {
		return orderService.findNeededOrderProducts(sellerNo, pageNum - 1, countPerPage);
	}
	
	@PostMapping("/3pl/order/register/{sellerNo}")
	@Operation(summary = "register order", description = "register order")
	public boolean register(@PathVariable Long sellerNo) {
		return orderService.register(sellerNo);
	}
	//발주 목록 조회 url 추가
	@GetMapping(value = {"/3pl/order/list", "/seller/order/list", "3pl/import/pre/list"})
	@Operation(summary = "find orders", description = "find orders by a seller")
	public OrderListDTO findOrders(Long sellerNo, int pageNum, int countPerPage) {
		return orderService.findOrders(sellerNo, pageNum - 1, countPerPage);
	}
	
	@GetMapping(value = {"/3pl/order/{orderNo}", "/seller/order/{orderNo}","/seller/import/pre/{orderNo}","/seller/import/order/detail/{orderNo}"})
	@Operation(summary = "find order products", description = "find order products of an order")
	public List<OrderProductDTO> findByOrderNo(@PathVariable Long orderNo) {
		return orderService.findByOrderNo(orderNo);
	}
	
	@GetMapping("/seller/import/pre-order")
	@Operation(summary = "find not imported orders", description = "find ordered but not imported products")
	public OrderListDTO findNotImportedOrdersBySeller(Long sellerNo, int pageNum, int countPerPage) {
		return orderService.findNotImportedOrdersBySeller(sellerNo, pageNum - 1, countPerPage);
	}
}
