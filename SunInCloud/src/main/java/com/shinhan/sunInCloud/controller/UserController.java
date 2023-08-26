package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.dto.UserListDTO;
import com.shinhan.sunInCloud.service.SellerService;
import com.shinhan.sunInCloud.service.ThreePLService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "User API Document")
public class UserController {
	private final SellerService sellerService;
	private final ThreePLService threePLService;
	
	@GetMapping(value = {"/wms/3pl/{threePLNo}", "/seller/match/{threePLNo}", "/3pl/mypage/{threePLNo}"})
	@Operation(summary = "detail of 3PL", description = "find the detail of a 3PL with the given 3PL number")
	public ThreePLDTO detailThreePL(@PathVariable Long threePLNo) {
		return threePLService.threePLDetail(threePLNo);
	}
	
	@GetMapping("/3pl/mypage/seller/list/{threePLNo}") 
	@Operation(summary = "contracted seller list", description = "find all the contracted seller list with a 3PL")
	public List<MatchingDTO> contractedSellerList(@PathVariable Long threePLNo) {
		return threePLService.findByContractedSeller(threePLNo);
	}
	
	@GetMapping(value = {"/wms/seller/{sellerNo}", "/3pl/match/{sellerNo}", "/3pl/mypage/seller/{sellerNo}", "/seller/mypage/{sellerNo}"})
	@Operation(summary = "detail of a seller", description = "find the detail of a seller")
	public SellerDTO sellerDetail(@PathVariable Long sellerNo) {
		return sellerService.sellerDetail(sellerNo);
	}
	
	@GetMapping("/seller/mypage/3pl/{sellerNo}")
	@Operation(summary = "detail of the contracted 3PL", description = "find the detail of contracted 3PL with a seller")
	public ThreePLDTO contractedThreePLDetail(@PathVariable Long sellerNo) {
		return sellerService.contractedThreePLDetail(sellerNo);
	}
	
	@GetMapping("/wms/seller/list")
	@Operation(summary = "all sellers", description = "find all sellers")
	public UserListDTO allSellers(int pageNum, int countPerPage) {
		return sellerService.findAllSeller(pageNum, countPerPage);
	}
	
	@GetMapping("/wms/3pl/list")
	@Operation(summary = "all 3PLs", description = "find all 3PL")
	public UserListDTO allThreePLs(int pageNum, int countPerPage) {
		return threePLService.findAllThreePL(pageNum, countPerPage);
	}
}
