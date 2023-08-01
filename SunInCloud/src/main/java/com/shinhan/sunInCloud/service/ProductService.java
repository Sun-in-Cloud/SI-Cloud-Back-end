package com.shinhan.sunInCloud.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	
	/**
	 * 화주사 아이디 기반 모든 상품 조회
	 * @param sellerNo
	 * @param pageNumber
	 * @param pageSize
	 * @return page에 해당하는 상품
	 */
	public Page<Product> findProductBySellerNo(Long sellerNo, int pageNumber, int pageSize) {
		return productRepository.findAllBySeller_SellerNo(sellerNo, PageRequest.of(pageNumber, pageSize));
	}
}
