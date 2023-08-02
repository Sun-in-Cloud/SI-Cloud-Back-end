package com.shinhan.sunInCloud.orders;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.service.OrderService;

@SpringBootTest
public class OrderTest {

	@Autowired
	OrderService orderService;
	
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
}
