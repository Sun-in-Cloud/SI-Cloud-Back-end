package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.service.DetailProductGroupService;
import com.shinhan.sunInCloud.service.ProductGroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product Group", description = "Product Group API Document")
public class ProductGroupController {
	private final ProductGroupService productGroupService;
	private final DetailProductGroupService detailProductGroupService;
	
	@GetMapping("/productGroup/list")
	@Operation(summary = "find product groups", description = "find all the product groups")
	public List<String> findProductGroups() {
		return productGroupService.findAll();
	}
	
	@GetMapping("/detailProductGroup/list/{sellerNo}")
	@Operation(summary = "find the detail product groups", description = "find the detail product groups")
	public List<String> findDetailProductGroups(@PathVariable Long sellerNo) {
		return detailProductGroupService.findByProductGroup(sellerNo);
	}
}
