package com.shinhan.sunInCloud.orders;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.OrderDTO;
import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.service.OrderService;
import com.shinhan.sunInCloud.service.ProductService;

@SpringBootTest
public class OrderTest {

	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	@Test
	void findNeededOrderProducts() {
		Long sellerNo = 8L;
		int pageNum = 1;
		int countPerPage = 7;
		List<OrderProductDTO> orderProducts = orderService.findNeededOrderProducts(sellerNo, pageNum, countPerPage);
		for (OrderProductDTO orderProduct : orderProducts) {
			System.out.println(orderProduct);
			Assertions.assertThat(orderProduct.getAmount()).isEqualTo(orderProduct.getEnoughStock() - orderProduct.getCurrentStock());
		}
	}
	
	@Test
	void findNeededOrderProductsZero() {
		Long sellerNo = 8L;
		int pageNum = 1;
		int countPerPage = 7;
		List<OrderProductDTO> orderProducts = orderService.findNeededOrderProducts(sellerNo, pageNum, countPerPage);
		Assertions.assertThat(orderProducts.size()).isEqualTo(0);
	}
	
	@Test
	void registerOrder() {
		Long sellerNo = 8L;
		boolean registered = orderService.register(sellerNo);
		Assertions.assertThat(registered).isTrue();
		List<Product> neededToOrderProducts = productService.findNeededToOrderBySellerNo(sellerNo);
		Assertions.assertThat(neededToOrderProducts.size()).isEqualTo(0);
	}
	
	@Test
	void findAllOrders() {
		Long sellerNo = 8L;
		List<OrderDTO> orders = orderService.findOrders(sellerNo, 0, 10);
		Assertions.assertThat(orders.size()).isEqualTo(5);
	}
	
	@Test 
	void findOrderAndOrderProducts() {
		Long orderNo = 26L;
		List<OrderProductDTO> orderProducts = orderService.findByOrderNo(orderNo);
		Assertions.assertThat(orderProducts.size()).isEqualTo(10);
	}
}
