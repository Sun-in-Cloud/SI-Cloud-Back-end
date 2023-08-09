package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.dto.SimpleProductDTO;
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
	
	@Transactional
	public void register(Long sellerNo) {
		List<SimpleProductDTO> products = null;
		String[] addresses = {"서울 강남구 개포동 190", "서울 용산구 서빙고로 17", "서울 용산구 독서당로 111", "서울 성북구 숭인로8길 80",
				"경기 파주시 심학산로 385", "경기 의정부시 용민로 10", "경기 수원시 팔달구 인계동 847-3", "경기 평택시 세교동 845",
				"강원 춘천시 동면 만천로 242", "강원 춘천시 안마산로 133", "강원 원주시 가곡로 120", "강원 강릉시 성덕포남로 39",
				"충북 청주시 상당구 방서동", "충북 청주시 흥덕구 대농로 55", "충북 진천군 대하로 114", "충북 음성군 금석택지길 34",
				"충남 아산시 갈산샛들로 67", "충남 천안시 서북구 미라2길 19", "충남 서산시 정주골2길 31", "충남 공주시 관골1길 7",
				"전북 군산시 하나운2길 15", "전북 전주시 덕진구 호반로 11", "전북 전주시 완산구 우전로 180", "전북 군산시 미룡로 42",
				"전남 여수시 웅천중앙로 47", "전남 목포시 옥암로 156", "전남 목포시 비파로 96", "전남 나주시 중야1길 37",
				"경북 구미시 옥계북로 69", "경북 구미시 문장로 111", "경북 김천시 혁신6로 57", "경북 포항시 북구 아치로 10",
				"경남 양산시 물금읍 새실로 11", "경남 진주시 새평거로 30", "경남 김해시 대청로 237", "경남 김해시 천곡로 26"};
		String[] channels = {"11번가", "스마트스토어", "G마켓", "카카오선물하기", "SSG닷컴", "옥션"};
		String[] orderers = {"임시아", "장원우", "고서준", "문민경", "오진아", "강다운", "정다운", "문하나", "임새롬", "이남규",
				"이재섭", "오혜린", "이혜림", "박유리", "신가영", "손민서", "김민지", "김지수", "이준혁"};
		
		String address = addresses[getRandomNumber(addresses.length)];
		String channel = channels[getRandomNumber(channels.length)];
		String orderer = orderers[getRandomNumber(orderers.length)];
		
		int numOfProduct = getRandomNumber(5) + 1;
		
		List<ShoppingProductDTO> shoppingProductDTOs = new ArrayList<>();
		
		while(shoppingProductDTOs.size() < numOfProduct) {
			
		}
	}
	
	/**
	 * 랜덤 숫자 생성해주는 메서드
	 * @param size
	 * @return
	 */
	private int getRandomNumber(int size) {
		return (int) (Math.random() * size);
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
	
	/**
	 * 수집되지 않은 주문건을 WMS으로 전송
	 * @param sellerNo
	 * @return
	 */
	public List<ShoppingDTO> sendOrderToWMS(Long sellerNo) {
		List<ShoppingDTO> shoppingDTOs = new ArrayList<>();
		List<Shopping> shoppings = findNotCollected(sellerNo);
		
		for(Shopping shopping: shoppings) {
			List<ShoppingProductDTO> shopingProductDTOs = new ArrayList<>();
			List<ShoppingProduct> shoppingProducts = findShoppingProduct(shopping.getExportNo());
			
			for(ShoppingProduct shoppingProduct : shoppingProducts) {
				shopingProductDTOs.add(shoppingProduct.toShoppingProductDTO());
			}
			
			shoppingDTOs.add(shopping.toShoppingDTO(shopingProductDTOs));
		}
		
		return shoppingDTOs;
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
	
	public List<ShoppingProductDTO> findShoppings(Long sellerNo) {
		List<ShoppingProductDTO> shoppingProductDTOs = new ArrayList<>();
		List<ShoppingProduct> shoppingProducts = shoppingProductRepository
				.findByShopping_Seller_SellerNoOrderByShopping_OrderDateDescProduct_ProductName(sellerNo);
		
		for(ShoppingProduct shoppingProduct: shoppingProducts) {
			ShoppingProductDTO shoppingProductDTO = shoppingProduct.toShoppingProductDTO();
			shoppingProductDTOs.add(shoppingProductDTO);
		}
		
		return shoppingProductDTOs;
	}
}
