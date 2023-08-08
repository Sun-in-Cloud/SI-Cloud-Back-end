package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.service.MarketingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MarketingController {

	private final MarketingService marketingService;
	
	@GetMapping("/seller/marketing/statistics")
	public StatisticsDTO getStatisticsBySeller(Long sellerNo) {
		return marketingService.getStatisticsBySeller(sellerNo);
	}
	
	@GetMapping("/seller/marketing/product")
	public StatisticsDTO getStatisticsOfProduct(String productNo) {
		return marketingService.getStatisticsOfProduct(productNo);
	}
}
