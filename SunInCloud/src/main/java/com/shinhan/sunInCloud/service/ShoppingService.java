package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.entity.ShoppingProduct;
import com.shinhan.sunInCloud.repository.ShoppingProductRepository;
import com.shinhan.sunInCloud.repository.ShoppingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingService {
	private final ShoppingRepository shoppingRepository;
	private final ShoppingProductRepository shoppingProductRepository;
	private final ProductService productService;
	private final SellerService sellerService;
	
	/**
	 * 주문 생성하는 메서드
	 * 
	 * @param sellerNo
	 * @param shoppingProductDTOs
	 * @return
	 */
	@Transactional
	public boolean register(Long sellerNo, List<ShoppingProductDTO> shoppingProductDTOs) {
		// 수집된 주문이 없는 경우
		if(shoppingProductDTOs.size() == 0) return false;
		
		List<ShoppingProduct> shoppingProducts = new ArrayList<>();
		Seller seller = sellerService.findById(sellerNo);
		StringBuilder exportNo = new StringBuilder("S");
		exportNo.append(sellerNo);
		exportNo.append("-");
		exportNo.append(new Date().getTime() % 10000000);
		
		Shopping shopping = shoppingProductDTOs.get(0)
				.toShopping(exportNo.toString(), "11번가", seller); 
		
		Shopping savedShopping = shoppingRepository.save(shopping);

		for(ShoppingProductDTO shoppingProductDTO: shoppingProductDTOs) {
			Product findedProduct = productService.findByProductNo(shoppingProductDTO.getProductNo());
			shoppingProducts.add(shoppingProductDTO.toShoppingProduct(findedProduct, savedShopping));
		}
		
		List<ShoppingProduct> savedProduct = shoppingProductRepository.saveAll(shoppingProducts);
		
		return (savedShopping != null && savedProduct != null);
	}
	
	/**
	 * 수집되지 않은 주문 내역 가져오는 메서드
	 * 
	 * @param sellerNo
	 * @return
	 */
	public List<Shopping> findNotCollected(Long sellerNo) {
		return shoppingRepository.findBySeller_SellerNoAndIsCollected(sellerNo, false);
	}

	/**
	 * 주문번호에 해당하는 상품 가져오는 메서드
	 * 
	 * @param exportNo
	 * @return
	 */
	public List<ShoppingProduct> findShoppingProduct(String exportNo) {
		return shoppingProductRepository.findByShopping_ExportNo(exportNo);
	}
}
