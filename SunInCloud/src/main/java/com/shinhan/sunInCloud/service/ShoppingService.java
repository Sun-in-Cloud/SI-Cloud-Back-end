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
	 * @param orderedProducts
	 * @return
	 */
	@Transactional
	public boolean register(Long sellerNo, List<ShoppingProductDTO> orderedProducts) {
		List<ShoppingProduct> products = new ArrayList<>();
		Seller seller = sellerService.findById(sellerNo);
		StringBuilder exportNo = new StringBuilder("S");
		exportNo.append(sellerNo);
		exportNo.append("-");
		exportNo.append(new Date().getTime() % 100000);
		
		Shopping shopping = Shopping
				.builder()
				.exportNo(exportNo.toString())
				.seller(seller)
				.salesChannel("11번가")
				.orderName(orderedProducts.get(0).getOrdererName())
				.address(orderedProducts.get(0).getAddress())
				.build();
		
		Shopping savedShopping = shoppingRepository.save(shopping);

		for(ShoppingProductDTO tmp: orderedProducts) {
			Product findedProduct = productService.findByProductNo(tmp.getProductNo());
			ShoppingProduct product = ShoppingProduct
					.builder()
					.amount(tmp.getAmount())
					.product(findedProduct)
					.sellingPrice(tmp.getSellingPrice())
					.shopping(savedShopping)
					.build();
			products.add(product);
		}
		
		List<ShoppingProduct> savedProduct = shoppingProductRepository.saveAll(products);
		
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

}
