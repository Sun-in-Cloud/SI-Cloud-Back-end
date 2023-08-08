package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportProductListDTO;
import com.shinhan.sunInCloud.dto.ImportProductPostDTO;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.service.SellerImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImportsController {
	
	private final SellerImportService sellerImportService;
	
	//발주 등록
	@PostMapping(value= {"/seller/import/register"})
	public boolean saveImport(@RequestBody ImportProductPostDTO dto) {
		return sellerImportService.saveImport(dto.getSellerNo(), dto.getOrderNo(), dto.getDtos());
	}
	
	
	//입고예정 리스트 상세 조회
	@GetMapping(value= {"/seller/import/pre/detail/{importNo}","/3pl/import/pre/{importNo}"})
	public ImportProductDTO seePreDetail(ImportProductDTO importProductDTO, @PathVariable Long importNo) {
		return sellerImportService.seePreDetail(importProductDTO, importNo);
	}
	//발주 상품 검색
	@GetMapping(value= {"/seller/import/search"})
	public List<Product> searchOrder(String productName) {
		return sellerImportService.searchOrder(productName);
	}
	
	@GetMapping(value= {"seller/import/list"})
	public ImportProductListDTO seeList (Long sellerNo, int pageNum, int countPerPage) {
		return sellerImportService.seeList(sellerNo, pageNum, countPerPage);
	}
}







