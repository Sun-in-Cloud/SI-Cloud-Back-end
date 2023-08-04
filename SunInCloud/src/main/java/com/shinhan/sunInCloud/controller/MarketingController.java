package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MarketingController {

	@GetMapping("/seller/marketing/statistics")
	public void getStatisticsBySeller(Long sellerNo) {
		
	}
}
