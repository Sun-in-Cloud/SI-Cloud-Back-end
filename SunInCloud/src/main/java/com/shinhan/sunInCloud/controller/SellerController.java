package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SellerController {
	private final SellerService sellerService;
	
	@GetMapping(value = {"/wms/seller/{sellerNo}", "/3pl/match/{sellerNo}"})
	public SellerDTO sellerDetail(@PathVariable Long sellerNo) {
		return sellerService.sellerDetail(sellerNo);
	}
}
