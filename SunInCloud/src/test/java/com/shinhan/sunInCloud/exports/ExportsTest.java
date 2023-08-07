package com.shinhan.sunInCloud.exports;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.entity.ExportProduct;
import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.service.ExportsService;
import com.shinhan.sunInCloud.service.ProductService;

@SpringBootTest
public class ExportsTest {
	@Autowired
	private ExportsService exportsService;
	@Autowired
	private ProductService productService;

	@Test
	void registerExport() {
		List<ExportProduct> products = new ArrayList<>();
		Timestamp orderDate = new Timestamp(new Date().getTime());

		Exports exports = Exports.builder().address("서울특별시 마포구 연남로 52").orderDate(orderDate).ordererName("홍길동")
				.salesChannel("11번가").build();

		Product tmp1 = productService.findByProductNo("8806338760904"); // 11000
		Product tmp2 = productService.findByProductNo("8806382650527"); // 4000
		Product tmp3 = productService.findByProductNo("8806165967330"); // 4000

		ExportProduct product1 = ExportProduct.builder().amount(5).product(tmp1).sellingPrice(9900).build();
		ExportProduct product2 = ExportProduct.builder().amount(6).product(tmp2).sellingPrice(3600).build();
		ExportProduct product3 = ExportProduct.builder().amount(2).product(tmp3).sellingPrice(4000).build();

		products.add(product1);
		products.add(product2);
		products.add(product3);

		List<ExportProduct> savedProducts = exportsService.register(exports, products);
		
		Assertions.assertThat(savedProducts.size()).isEqualTo(products.size());
	}
}
