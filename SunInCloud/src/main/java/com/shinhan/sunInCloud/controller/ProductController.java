package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping("/seller/product/register")
	public boolean register(@RequestBody ProductDTO productDTO) {
		return productService.register(productDTO);
	}
	
	@PutMapping("/seller/product/edit")
	public ProductDTO update(@RequestBody ProductDTO productDTO) {
		return productService.update(productDTO);
	}
	
	@DeleteMapping("/seller/product/delete")
	public boolean delete(@RequestBody ProductDTO productDTO) {
		return productService.delete(productDTO.getProductNo());
	}
}
