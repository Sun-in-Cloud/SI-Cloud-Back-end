package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ImportListDTO;
import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportProductListDTO;
import com.shinhan.sunInCloud.dto.ImportProductPostDTO;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.service.SellerImportService;
import com.shinhan.sunInCloud.service.ThreePLImportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "import", description = "Import API Document")
public class ImportsController {
	
	private final SellerImportService sellerImportService;
	private final ThreePLImportService threePLImportService;
	
	//발주 등록
	@PostMapping(value= {"/seller/import/register"})
	@Operation(summary = "saveImport", description = "save the import")
	public boolean saveImport(@RequestBody ImportProductPostDTO dto) {
		return sellerImportService.saveImport(dto.getSellerNo(), dto.getOrderNo(), dto.getImportList());
	}
	
	
	//입고예정 리스트 상세 조회, 내역리스트 상세조회
	@GetMapping(value= {"/seller/import/pre/detail/{importNo}","/3pl/import/pre/{importNo}"})
	@Operation(summary = "seePreDetail", description = "find the detail of the pre-import list")
	public List<ImportProductDTO>  seePreDetail( @PathVariable Long importNo) {
		return sellerImportService.seePreDetail(importNo);
	}
	//발주 상품 검색
	@GetMapping(value= {"/seller/import/search"})
	@Operation(summary = "searchOrder", description = "find the products of the seller which are needed to be ordered due to the small amount of stock")
	public List<Product> searchOrder(String productName, Long sellerNo) {
		return sellerImportService.searchOrder(productName, sellerNo);
	}
	
	@GetMapping("/seller/import/pre/list")
	@Operation(summary = "getPreImportList", description = "find pre-import list of the seller which is searched by the given id")
	public ImportListDTO getPreImportList(Long sellerNo, int pageNum, int countPerPage) {
		return sellerImportService.findPreImportList(sellerNo, pageNum - 1, countPerPage);
	}
	
	@GetMapping(value= {"seller/import/list"})
	@Operation(summary = "seeList", description = "find imports of the seller which is searched by the given id")
	public ImportProductListDTO seeList (Long sellerNo, int pageNum, int countPerPage) {
		return sellerImportService.seeList(sellerNo, pageNum - 1, countPerPage);
	}
	
	@GetMapping(value= {"/3pl/import/register"})
	@Operation(summary = "goRegister", description = "register the import")
	public List<ImportProductDTO> goRegister(Long importNo) {
		return threePLImportService.goRegister(importNo);
	}
	
	@PostMapping(value=  {"/3pl/import/register"})
	@Operation(summary = "saveThreePLImport", description = "save the import of the 3PL")
	public boolean saveThreePLImport(@RequestBody ImportProductPostDTO dto) {
		return threePLImportService.saveImport(dto.getSellerNo(), dto.getImportNo(), dto.getImportList());
	}
	
	@GetMapping(value= {"/seller/import/{importNo}"})
	@Operation(summary = "seeDetail", description = "find the detail of an import")
	public List<ImportProductDTO> seeDetail(@PathVariable Long importNo){
		return sellerImportService.seeDetail(importNo);
	}
}







