package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.MatchingConditionDTO;
import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.MatchingSellerListDTO;
import com.shinhan.sunInCloud.service.MatchingService;
import com.shinhan.sunInCloud.service.WarehouseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MatchingController {
	private final WarehouseService warehouseService;
	private final MatchingService matchingService;
	
	@GetMapping("/3pl/match/left/{threePLNo}")
	public List<String> findLeftLocation(@PathVariable Long threePLNo) {
		return warehouseService.findLeftLocation(threePLNo);
	}
	
	@PostMapping("/3pl/match/contract")
	public boolean contract(@RequestBody MatchingDTO matchingDTO) {
		return matchingService.contract(matchingDTO);
	}
	
	@GetMapping("/3pl/match/list")
	public MatchingSellerListDTO searcingSellerByCondition(MatchingConditionDTO matchingConditionDTO) {
		return matchingService.searchingSellerByCondition(matchingConditionDTO);
	}
}
