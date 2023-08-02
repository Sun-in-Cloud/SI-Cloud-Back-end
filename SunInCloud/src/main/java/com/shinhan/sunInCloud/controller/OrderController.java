package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
