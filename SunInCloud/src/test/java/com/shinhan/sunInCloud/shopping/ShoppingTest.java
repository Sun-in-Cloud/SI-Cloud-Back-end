package com.shinhan.sunInCloud.shopping;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.service.ShoppingService;

@SpringBootTest
public class ShoppingTest {
	@Autowired
	ShoppingService shoppingService;

//	@Test
	public void register() {
		Long sellerNo = 8L;
		List<ShoppingProductDTO> products = new ArrayList<>();
		String ordererName = "김홍대";
		String address = "서울특별시 마포구 양화로 지하160";
		String[] productName = { "룩엣마이아이즈 샤이닝베이지", "반짝눈물라이너 태양빛눈물", "생크림블러셔 자몽딸기", "디어달링틴트 리얼레드", "미니애니쿠션" };
		String[] productNo = { "8806165967330", "8806382609914", "8806199416347", "8806382685093", "8806165973164" };
		int[] amount = { 5, 2, 3, 1, 3 };
		int[] sellingPrice = { 19000, 14400, 24000, 4260, 34500 };
		
		for(int i = 0; i < productName.length; i++) {
			ShoppingProductDTO product = ShoppingProductDTO
					.builder()
					.address(address)
					.ordererName(ordererName)
					.productName(productName[i])
					.productNo(productNo[i])
					.amount(amount[i])
					.sellingPrice(sellingPrice[i])
					.build();
			
			products.add(product);
		}
		
		boolean result = shoppingService.register(sellerNo, products);
		Assertions.assertThat(result).isEqualTo(true);
	}
	
//	@Test
	public void findNotCollected() {
		Long sellerNo = 8L;
		List<Shopping> shoppings = shoppingService.findNotCollected(sellerNo);
		Assertions.assertThat(shoppings.size()).isNotNull();
	}
	
//	@Test
	public void sendToWMS() {
		Long sellerNo = 8L;
		List<ShoppingDTO> shoppings = shoppingService.sendOrderToWMS(sellerNo);
		
		for(ShoppingDTO shopping: shoppings) {
			System.out.println(shopping);
		}
	}
	
	@Test
	public void findShoppings() {
		Long sellerNo = 8L;
		List<ShoppingProductDTO> shoppings = shoppingService.findShoppings(sellerNo);
		
		for(ShoppingProductDTO s : shoppings) {
			System.out.println(s);
		}
	}
}
