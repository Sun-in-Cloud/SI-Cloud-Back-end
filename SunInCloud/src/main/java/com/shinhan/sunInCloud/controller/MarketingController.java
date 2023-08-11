package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ChannelSalesListDTO;
import com.shinhan.sunInCloud.dto.DangerousProductDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.service.MarketingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MarketingController {

	private final MarketingService marketingService;
	
	@GetMapping("/seller/marketing/statistics")
	public StatisticsDTO getStatisticsBySeller(Long sellerNo) {
		return marketingService.getStatistics(sellerNo);
	}
	
	@GetMapping("/seller/marketing/product")
	public StatisticsDTO getStatisticsOfProduct(String productNo) {
		return marketingService.getStatistics(productNo);
	}
	
	@GetMapping("/seller/marketing/channel")
	public ChannelSalesListDTO getStatisticsOfChannelsBySeller(Long sellerNo) {
		return marketingService.getTotalSalesOfChannelsBySeller(sellerNo);
	}
	
	@GetMapping("/seller/marketing/danger")
	public List<DangerousProductDTO> getDangerousProducts(Long sellerNo) {
		return marketingService.getDangerousProducts(sellerNo);
	}
}
