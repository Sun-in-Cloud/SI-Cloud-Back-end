package com.shinhan.sunInCloud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	/**
	 * 상품 등록 메서드
	 * 
	 * @param product
	 * @return 등록된 상품
	 */
	public Product register(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> registerAll(List<Product> products) {
		return productRepository.saveAll(products);
	}
}
