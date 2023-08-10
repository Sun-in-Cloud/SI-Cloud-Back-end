package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ProductDTO;
import com.shinhan.sunInCloud.dto.ProductListDTO;
import com.shinhan.sunInCloud.entity.DetailProductGroup;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ProductHistoryRepository;
import com.shinhan.sunInCloud.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final SellerService sellerService;
	private final DetailProductGroupService detailProductGroupService;
	
	private final ProductRepository productRepository;
	private final ProductHistoryRepository productHistoryRepository;

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
	 * 상품 등록 메서드
	 * @param productDTO
	 * @return 상품이 정상적으로 등록되면 true, 아니면 false
	 */
	public boolean register(ProductDTO productDTO) {
		if (productRepository.existsByProductNameAndSeller_SellerNo(productDTO.getProductName(), productDTO.getSellerNo())) {
			return false;
		}
		Seller seller = sellerService.findById(productDTO.getSellerNo());
		DetailProductGroup detailProductGroup = detailProductGroupService.findByGroupName(productDTO.getProductGroup());
		Product product = productDTO.toProduct(seller, detailProductGroup);
		if (productRepository.save(product) == null) {
			return false;
		}
		return true;
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
	public ProductListDTO findProductBySellerNo(Long sellerNo, int pageNumber, int pageSize) {
		List<ProductDTO> productDTOs = new ArrayList<>();
		Page<Product> products = productRepository.findAllBySeller_SellerNoAndIsActiveOrderByProductName(sellerNo, PageRequest.of(pageNumber, pageSize), true);
		for (Product product : products) {
			productDTOs.add(ProductDTO.builder().productNo(product.getProductNo())
					.productGroup(product.getDetailProductGroup().getGroupName())
					.productName(product.getProductName()).safetyStock(product.getSafetyStock())
					.currentStock(product.getCurrentStock()).enoughStock(product.getEnoughStock()).build());
		}
		Long count = productRepository.countBySeller_SellerNo(sellerNo);
		Long totalPage = count / pageSize;
		if (count % pageSize > 0) {
			++totalPage;
		}
		return ProductListDTO.builder().totalPage(totalPage).products(productDTOs).build();
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
	public ProductDTO findById(String productNo) {
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

	/**
	 * 입력받은 수정 사항을 update해주는 메서드
	 * @param productDTO
	 * @return update된 DTO
	 * 작성자: 손준범
	 */
	@Transactional
	public ProductDTO update(ProductDTO productDTO) {
		// 이미 있는 상품을 update하는 것이기 때문에 null이 오지 않음을 보장함
		Product product = findByProductNo(productDTO.getProductNo());
		product.updateProductByProductDTO(productDTO);
		Product savedProduct = productRepository.save(product);
		return savedProduct.toProductDTO();
	}

	/**
	 * 바코드 번호로 delete하는 메서드
	 * 해당 상품의 존재 여부를 확인하고, 있으면 삭제
	 * @param productNo
	 * @return 삭제 성공시 true, 실패시 false
	 * 작성자: 손준범
	 */
	@Transactional
	public boolean delete(String productNo) {
		Product product = findByProductNo(productNo);
		if (product == null) {
			return false;
		}
		product.setIsActive(false);
		return true;
	}
	
	/**
	 * 안전재고보다 현재재고가 더 낮은 상품을 화주사 번호로 조회하는 메서드
	 * @param sellerNo
	 * @param pageable
	 * @return
	 * 작성자: 손준범
	 */
	public Page<Product> findNeededToOrderBySellerNo(Long sellerNo, Pageable pageable) {
		return productRepository.findByNeededToOrder(sellerNo, pageable);
	}
	
	/**
	 * 발주가 필요한 상품을 전부 조회하는 메서드
	 * @param sellerNo
	 * @return 발주가 필요한 상품 리스트
	 */
	public List<Product> findNeededToOrderBySellerNo(Long sellerNo) {
		return productRepository.findByNeededToOrder(sellerNo);
	}
	
	/**
	 * 발주가 필요한 상품의 개수 조회 메서드
	 * @param sellerNo
	 * @return 총 상품의 개수
	 */
	public Long countNeededToOrder(Long sellerNo) {
		return productRepository.countNeededToOrder(sellerNo);
	}
}
