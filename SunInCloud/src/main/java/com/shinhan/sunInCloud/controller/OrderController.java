package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.OrderDTO;
import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	@GetMapping("/3pl/order/auto-list")
	public List<OrderProductDTO> findNeededOrderProducts(Long sellerNo, int pageNum, int countPerPage) {
		return orderService.findNeededOrderProducts(sellerNo, pageNum, countPerPage);
	}
	
	@PostMapping("/3pl/order/register/{sellerNo}")
	public boolean register(@PathVariable Long sellerNo) {
		return orderService.register(sellerNo);
	}
	
	@GetMapping("/3pl/order/list")
	public List<OrderDTO> findOrders(Long sellerNo, int pageNum, int countPerPage) {
		return orderService.findOrders(sellerNo, pageNum, countPerPage);
	}
}
