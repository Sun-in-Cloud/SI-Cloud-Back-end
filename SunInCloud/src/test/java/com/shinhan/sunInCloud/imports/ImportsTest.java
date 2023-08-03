package com.shinhan.sunInCloud.imports;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.service.OrderService;
import com.shinhan.sunInCloud.service.SellerImportService;

@SpringBootTest
public class ImportsTest {
	
	@Autowired
	SellerImportService importService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SellerImportService sellerImportService;
	
	//발주 목록 조회
	@Test
	void seeOrderList() {
		Long seller=8L;
		List<Order> order = sellerImportService.findBySellerNo(8L);
		Assertions.assertThat(order.size()).isEqualTo(5);
	}
	
	//발주 내역 상세 조회
	@Test
	void seeOrdersDetail() {
		Long orderNo = 26L;
		List<OrderProductDTO> orderProducts = orderService.findByOrderNo(orderNo);
		Assertions.assertThat(orderProducts.size()).isEqualTo(10);
	}
	
//	@Test
//	void seePreList() {
//		List<Imports> imports = new ArrayList<>();
//		Timestamp timeStamp = new Timestamp(new Date().getTime());
//		Imports im = Imports.builder().requestDate(timeStamp)
//				.importDate(timeStamp).build();	
//	}
}
