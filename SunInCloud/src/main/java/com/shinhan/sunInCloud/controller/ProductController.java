package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ProductDTO;
import com.shinhan.sunInCloud.dto.ProductListDTO;
import com.shinhan.sunInCloud.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API Document")
public class ProductController {

	private final ProductService productService;
	
	@GetMapping(value = {"/3pl/product/list", "/seller/product/list"})
	@Operation(summary = "find products", description = "find all products of a seller")
	public ProductListDTO findProductBySellerNo(Long sellerNo, int pageNum, int countPerPage) {
		return productService.findProductBySellerNo(sellerNo, pageNum - 1, countPerPage);
	}
	
	@GetMapping("/seller/product/{productNo}")
	@Operation(summary = "find a product", description = "find a product by a given product number")
	public ProductDTO findByProductNo(@PathVariable String productNo) {
		return productService.findById(productNo);
	}
	
	@PostMapping("/seller/product/register/{sellerNo}")
	@Operation(summary = "register a product", description = "register a product")
	public boolean register(@RequestBody ProductDTO productDTO, @PathVariable Long sellerNo) {
		productDTO.setSellerNo(sellerNo);
		return productService.register(productDTO);
	}
	
	@PutMapping("/seller/product/edit")
	@Operation(summary = "update a product", description = "update a product")
	public ProductDTO update(@RequestBody ProductDTO productDTO) {
		return productService.update(productDTO);
	}
	
	@DeleteMapping("/seller/product/delete")
	@Operation(summary = "delete a product", description = "delete a product")
	public boolean delete(@RequestBody ProductDTO productDTO) {
		return productService.delete(productDTO.getProductNo());
	}
}
