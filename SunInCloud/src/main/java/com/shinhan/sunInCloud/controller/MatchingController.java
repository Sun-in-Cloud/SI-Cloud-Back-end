package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.MatchingConditionDTO;
import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.MatchingListDTO;
import com.shinhan.sunInCloud.service.MatchingService;
import com.shinhan.sunInCloud.service.WarehouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Matching", description = "Matching API Document")
public class MatchingController {
	private final WarehouseService warehouseService;
	private final MatchingService matchingService;
	
	@GetMapping("/3pl/match/left/{threePLNo}")
	@Operation(summary = "find remain locations", description = "find remain locations of a 3PL")
	public List<String> findLeftLocation(@PathVariable Long threePLNo) {
		return warehouseService.findLeftLocation(threePLNo);
	}
	
	@PostMapping("/3pl/match/contract")
	@Operation(summary = "contract", description = "make a contract")
	public boolean contract(@RequestBody MatchingDTO matchingDTO) {
		return matchingService.contract(matchingDTO);
	}
	
	@GetMapping("/3pl/match/list")
	@Operation(summary = "search sellers with conditions", description = "search sellers by given conditions to make a contract")
	public MatchingListDTO searcingSellerByCondition(MatchingConditionDTO matchingConditionDTO) {
		return matchingService.searchingSellerByCondition(matchingConditionDTO);
	}
	
	@GetMapping("/seller/match/list")
	@Operation(summary = "search 3PL with conditions", description = "search 3PL companies with given conditions")
	public MatchingListDTO searchingThreePLByCondition(MatchingConditionDTO matchingConditionDTO) {
		return matchingService.searchingThreePLByCondition(matchingConditionDTO);
	}
}
