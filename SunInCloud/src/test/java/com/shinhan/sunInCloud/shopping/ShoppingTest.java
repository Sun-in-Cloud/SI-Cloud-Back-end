package com.shinhan.sunInCloud.shopping;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.dto.SimpleProductDTO;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.service.ProductService;
import com.shinhan.sunInCloud.service.ShoppingService;

@SpringBootTest
public class ShoppingTest {
	@Autowired
	ShoppingService shoppingService;
	@Autowired
	ProductService productService;
	
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
	
//	@Test
	public void findShoppings() {
		Long sellerNo = 8L;
		List<ShoppingProductDTO> shoppings = shoppingService.findShoppings(sellerNo);
		
		for(ShoppingProductDTO s : shoppings) {
			System.out.println(s);
		}
	}
	
//	@Test
	public void test() {
		Long sellerNo = 8L;
		
		List<SimpleProductDTO> products = productService.findByAllProductSimpledata(sellerNo);
		for(SimpleProductDTO product : products) {
			System.out.println(product);
		}
	}
	
	@Test
	public void autoRegister() {
		Long sellerNo = 8L;
		
		List<ShoppingProductDTO> shoppingProductDTOs = shoppingService.register(sellerNo);
		for(ShoppingProductDTO shoppingProductDTO : shoppingProductDTOs) {
			System.out.println(shoppingProductDTO);
		}
	}
}
