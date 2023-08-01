package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ProductDTO;
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
	
	/**
	 * 상품 일괄 등록 메서드
	 * 
	 * @param products
	 * @return 등록된 상품 리스트
	 */
	public List<Product> registerAll(List<Product> products) {
		return productRepository.saveAll(products);
	}
	
	/**
	 * 화주사 아이디 기반 모든 상품 조회
	 * @param sellerNo
	 * @param pageNumber
	 * @param pageSize
	 * @return page에 해당하는 상품 리스트
	 */
	public List<ProductDTO> findProductBySellerNo(Long sellerNo, int pageNumber, int pageSize) {
		List<ProductDTO> productDTOs = new ArrayList<>();
		Page<Product> products = productRepository.findAllBySeller_SellerNo(sellerNo, PageRequest.of(pageNumber, pageSize));
		for (Product product : products) {
			productDTOs.add(ProductDTO.builder().productNo(product.getProductNo())
					.productGroup(product.getDetailProductGroup().getGroupName())
					.productName(product.getProductName()).safetyStock(product.getSafetyStock())
					.currentStock(product.getCurrentStock()).enoughStock(product.getEnoughStock()).build());
		}
		return productDTOs;
	}
	
	public Product findByProductNo(String productNo) {
		return productRepository.findById(productNo).orElse(null);
	}
	
	/**
	 * productNo를 기반으로 product를 검색하고, ProductDTO로 변환후 리턴
	 * @param productNo
	 * @return 없으면 null, 있으면 변환된 ProductDTO
	 * 작성자 : 손준범
	 */
	public ProductDTO findDTOByProductNo(String productNo) {
		Product product = productRepository.findById(productNo).orElse(null);
		if (product == null) {
			return null;
		}
		return ProductDTO.builder().productNo(product.getProductNo())
				.productGroup(product.getDetailProductGroup().getGroupName())
				.productName(product.getProductName()).safetyStock(product.getSafetyStock())
				.currentStock(product.getCurrentStock()).enoughStock(product.getEnoughStock())
				.importPrice(product.getImportPrice()).consumerPrice(product.getConsumerPrice()).build();
	}
}
