package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ProductGroupDTO;
import com.shinhan.sunInCloud.service.DetailProductGroupService;
import com.shinhan.sunInCloud.service.ProductGroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductGroupController {
	private final ProductGroupService productGroupService;
	private final DetailProductGroupService detailProductGroupService;
	
	@GetMapping("/productGroup/list")
	public List<ProductGroupDTO> findProductGroups() {
		return productGroupService.findAll();
	}
	
	@GetMapping("/detailProductGroup/list/{sellerNo}")
	public List<ProductGroupDTO> findDetailProductGroups(@PathVariable Long sellerNo) {
		return detailProductGroupService.findByProductGroup(sellerNo);
	}
}
