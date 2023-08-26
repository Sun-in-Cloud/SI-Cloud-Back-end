package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.service.ShoppingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Shopping", description = "Shopping API Document")
public class ShoppingController {
	private final ShoppingService shoppingService;
	
	@PostMapping("/shop/order/{sellerNo}")
	@Operation(summary = "register shopping products", description = "register shopping products")
	public List<ShoppingProductDTO> register(@PathVariable Long sellerNo) {
		return shoppingService.register(sellerNo);
	}
	
	@GetMapping("/shop/order/send/{sellerNo}")
	@Operation(summary = "deliver order to WMS", description = "deliver order to WMS")
	public List<ShoppingDTO> sendOrderToWMS(@PathVariable Long sellerNo) {
		return shoppingService.sendOrderToWMS(sellerNo);
	}
	
	@GetMapping("/shop/list")
	@Operation(summary = "find all the orders from a seller", description = "find all the orders from a seller from the shopping platfomrs such as 11st, ssg.com")
	public List<ShoppingProductDTO> findShoppings(Long sellerNo) {
		return shoppingService.findShoppings(sellerNo);
	}
}
