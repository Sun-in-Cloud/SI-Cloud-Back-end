package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.service.ThreePLService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ThreePLController {
	private final ThreePLService threePLService;
	
	@GetMapping(value = {"/wms/3pl/{threePLNo}", "/seller/match/{threePLNo}", "/3pl/mypage/{threePLNo}"})
	public ThreePLDTO detailThreePL(@PathVariable Long threePLNo) {
		return threePLService.threePLDetail(threePLNo);
	}
	
	@GetMapping("/3pl/mypage/seller/list/{threePLNo}") 
	public List<MatchingDTO> contractedSellerList(@PathVariable Long threePLNo) {
		return threePLService.findByContractedSeller(threePLNo);
	}
	
}
