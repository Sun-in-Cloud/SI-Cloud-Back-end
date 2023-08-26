package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ChannelSalesListDTO;
import com.shinhan.sunInCloud.dto.DangerousProductDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.service.MarketingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Marketing", description = "Marketing API Document")
public class MarketingController {

	private final MarketingService marketingService;
	
	@GetMapping("/seller/marketing/statistics")
	@Operation(summary = "find statistics", description = "find statistics of a seller")
	public StatisticsDTO getStatisticsBySeller(Long sellerNo) {
		return marketingService.getStatistics(sellerNo);
	}
	
	@GetMapping("/seller/marketing/product")
	@Operation(summary = "find statistics of product", description = "find statistics of specific product of a seller")
	public StatisticsDTO getStatisticsOfProduct(String productNo) {
		return marketingService.getStatistics(productNo);
	}
	
	@GetMapping("/seller/marketing/channel")
	@Operation(summary = "statistics of channels", description = "find the statistics of channels by a seller")
	public ChannelSalesListDTO getStatisticsOfChannelsBySeller(Long sellerNo) {
		return marketingService.getTotalSalesOfChannelsBySeller(sellerNo);
	}
	
	@GetMapping("/seller/marketing/danger")
	@Operation(summary = "find dangerous products", description = "find dangerous products which are under the safety stock")
	public List<DangerousProductDTO> getDangerousProducts(Long sellerNo) {
		return marketingService.getDangerousProducts(sellerNo);
	}
}
