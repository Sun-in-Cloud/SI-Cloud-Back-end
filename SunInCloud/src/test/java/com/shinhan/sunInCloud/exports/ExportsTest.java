package com.shinhan.sunInCloud.exports;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.ExportInvoiceDTO;
import com.shinhan.sunInCloud.dto.ExportProductDTO;
import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.service.ExportsService;

@SpringBootTest
public class ExportsTest {
	@Autowired
	private ExportsService exportsService;

//	@Test
	void registerExport() {
		Long sellerNo = 8L;
		List<ExportsDTO> exports = exportsService.register(sellerNo, 0, 10);
		
		Assertions.assertThat(exports.size()).isNotZero();
	}
	
//	@Test
	void findAllExport() {
		Long sellerNo = 8L;
		List<ExportsDTO> exports = exportsService.findExports(sellerNo, 0, 10);
		
		Assertions.assertThat(exports.size()).isNotZero();
	}
	
//	@Test
	void exportDetail() {
		String exportNo = "S8-63592";
		List<ExportProductDTO> products = exportsService.exportDetail(exportNo, 0, 10);
		
		Assertions.assertThat(products.size()).isNotZero();
		
	}
	
	@Test
	void printInvoice() {
		String exportNo = "S8-63592";
		String[] productNo = {"8806165967330", "8806382609914", "8806199416347"};
		int[] amount = {5, 2, 3};
		
		List<ExportInvoiceDTO> products = new ArrayList<>();
		for(int i = 0; i < productNo.length; i++) {
			ExportInvoiceDTO product = ExportInvoiceDTO.builder().productNo(productNo[i]).amount(amount[i]).build();
			products.add(product);
		}
		
		List<ExportInvoiceDTO> exportProducts = exportsService.printInvoice(exportNo, products);
		for(ExportInvoiceDTO exportProduct: exportProducts) {
			System.out.println(exportProduct);
		}
	}
}
