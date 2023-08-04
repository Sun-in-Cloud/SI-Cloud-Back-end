package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.service.MarketingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MarketingController {

	private final MarketingService marketingService;
	
	@GetMapping("/seller/marketing/statistics")
	public List<NumberOfSalesDTO> getStatisticsBySeller(Long sellerNo) {
		return marketingService.getStatisticsBySeller(sellerNo);
	}
}
