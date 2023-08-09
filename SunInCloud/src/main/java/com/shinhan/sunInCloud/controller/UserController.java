package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.service.SellerService;
import com.shinhan.sunInCloud.service.ThreePLService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final SellerService sellerService;
	private final ThreePLService threePLService;
	
	@GetMapping(value = {"/wms/3pl/{threePLNo}", "/seller/match/{threePLNo}", "/3pl/mypage/{threePLNo}"})
	public ThreePLDTO detailThreePL(@PathVariable Long threePLNo) {
		return threePLService.threePLDetail(threePLNo);
	}
	
	@GetMapping("/3pl/mypage/seller/list/{threePLNo}") 
	public List<MatchingDTO> contractedSellerList(@PathVariable Long threePLNo) {
		return threePLService.findByContractedSeller(threePLNo);
	}
	
	@GetMapping(value = {"/wms/seller/{sellerNo}", "/3pl/match/{sellerNo}", "/3pl/mypage/seller/{sellerNo}", "/seller/mypage/{sellerNo}"})
	public SellerDTO sellerDetail(@PathVariable Long sellerNo) {
		return sellerService.sellerDetail(sellerNo);
	}
	
	@GetMapping("/seller/mypage/3pl/{sellerNo}")
	public ThreePLDTO contractedThreePLDetail(@PathVariable Long sellerNo) {
		return sellerService.contractedThreePLDetail(sellerNo);
	}
}
