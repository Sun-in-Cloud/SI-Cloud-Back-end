package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ProductDTO;
import com.shinhan.sunInCloud.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@GetMapping(value = {"/3pl/product/list", "/seller/product/list"})
	public List<ProductDTO> findProductBySellerNo(Long sellerNo, int pageNum, int countPerPage) {
		return productService.findProductBySellerNo(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping("/seller/product/{productNo}")
	public ProductDTO findByProductNo(@PathVariable String productNo) {
		return productService.findById(productNo);
	}
}
