package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.service.ShoppingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShoppingController {
	private final ShoppingService shoppingService;
	
	@PostMapping("/shop/order")
	public boolean register(@RequestBody ShoppingDTO shoppingDTO) {
		return shoppingService.register(shoppingDTO.getSellerNo(), shoppingDTO.getOrderedProducts());
	}
	
	@GetMapping("/shop/order/send/{sellerNo}")
	public List<ShoppingDTO> sendOrderToWMS(@PathVariable Long sellerNo) {
		return shoppingService.sendOrderToWMS(sellerNo);
	}
}
