package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportProductListDTO;
import com.shinhan.sunInCloud.service.SellerImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImportsController {
	
	private final SellerImportService sellerImportService;
	
	//발주 등록
	@PostMapping(value= {"/seller/import/register"})
	public boolean saveImport(Long sellerNo, List<ImportProductDTO> importProductDTOs) {
		return sellerImportService.saveImport(sellerNo, importProductDTOs);
	}
	
	
	//입고예정 리스트 목록 조회
	@GetMapping(value= {"/seller/import/pre/list"})
	public ImportProductListDTO seePreList (Long sellerNo, int pageNumber, int countPerPage){
		return sellerImportService.seePreList(sellerNo, pageNumber, countPerPage);
	}
}
