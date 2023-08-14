package com.shinhan.sunInCloud.imports;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.shinhan.sunInCloud.dto.ExportInvoiceDTO;
import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportProductListDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.dto.ProductDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.OrderProduct;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.ProductRepository;
import com.shinhan.sunInCloud.service.OrderService;
import com.shinhan.sunInCloud.service.SellerImportService;
import com.shinhan.sunInCloud.service.ThreePLImportService;

@SpringBootTest
public class ImportsTest {
	
	@Autowired
	SellerImportService importService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SellerImportService sellerImportService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ImportsProductRepository importProductRepository;
	
	@Autowired
	ImportsRepository importsRepository;
	
	@Autowired
	ThreePLImportService threePLImportService;
	
	
	//판매자
	
	//발주 목록 조회
	//@Test

//	void seeOrderList() {
//		Long seller=8L;
//		List<Order> order = sellerImportService.findBySellerNo(seller);
//		Assertions.assertThat(order.size()).isEqualTo(5);
//	}
//	
//	//발주 내역 상세 조회
//	//@Test
//	void seeOrdersDetail() {
//		Long orderNo = 26L;
//		List<OrderProductDTO> orderProducts = orderService.findByOrderNo(orderNo);
//		Assertions.assertThat(orderProducts.size()).isEqualTo(10);
//	}
//	
//	//발주 조회 ->검색
//	//@Test
//	void searchOrder() {
//		String productName="룩엣마이아이즈 샤이닝베이지";
//		Product product  = sellerImportService.searchOrder(productName);
//		System.out.println(product.getSafetyStock());
//			
//	}
//	
//	@Test
//	void saveImports() {
//		Long sellerNo=8L;
//		int pageNumber = 0;
//		int pageSize = 10;
//		Long orderNo = 26L;
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<Product> p = productRepository.findAllBySeller_SellerNo(sellerNo, pageable);
//		List<ImportProductDTO> importProductDTOs = new ArrayList<>();
//		for(Product product : p.getContent()) {
//			ImportProductDTO importPRoductDTO = ImportProductDTO.builder()
//					.productNo(product.getProductNo())
//					.requestAmount(20)
//					.importAmount(20)
//					.build();
//			importProductDTOs.add(importPRoductDTO);
//		}
//		
//		boolean saved = sellerImportService.saveImport(sellerNo, orderNo, importProductDTOs);
//		Assertions.assertThat(saved);
//		
//	}
//	//입고 예정 리스트 목록 조회
//	//@Test
//	void seePreList() {
//		Long sellerNo=8L;
//		int pageNumber = 0;
//		int countperPage = 10;
//		ImportProductListDTO imports = sellerImportService.seePreList(sellerNo, pageNumber, countperPage);
//		Assertions.assertThat(imports.getImportproduct()).isNotEmpty();
//	}
//	
//	//입고 예정 리스트 상세 조회
//	//@Test
//	void seePreDetail() {
//		Long importNo = 150L;
//		List<ImportProduct> importProduct = importProductRepository.findByImports_ImportNo(importNo);
//		for(ImportProduct im : importProduct) {
//			System.out.println(im.getImportProductNo());
//		}
//		Assertions.assertThat(importProduct.size()).isEqualTo(10);
//	}

	//입고 내역 리스트 목록 조회
	//@Test
//	void seeImportList() {
//		Long sellerNo = 8L;
//		int pageNumber = 0;
//		int countperPage = 10;
//		List<Imports> importProduct = importsRepository.findAllBySeller_SellerNo(sellerNo);
//		
//		for(Imports im: importProduct) {
//			System.out.println(im.getImportNo());
//		}
//		Assertions.assertThat(importProduct.size()).isEqualTo(expected);
//		
//	}
	
	//3pl
	//@Test
	void goRegister() {
		Long importNo = 150L;
		int pageNum = 0;
		int countPerPage = 10;
		List<ImportProductDTO> im = threePLImportService.goRegister(importNo);
		for(ImportProductDTO i: im) {
			System.out.println(i.getProductNo());
		}
		
	}	
	
	//3PL 입고 등록
	@Test
	void isRegister() {
		 Long sellerNo = 8L;
		 String productNo = "8806165967330";
		 Long importNo=2L;
		 List<ImportProductDTO> importProductDTOs = new ArrayList<>();
	        importProductDTOs.add(ImportProductDTO.builder()
	                .productNo(productNo)
	                .importAmount(10)
	                .requestAmount(5)
	                .importNo(importNo)
	                .productName("as")
	                .build());
  
		    boolean importProducts = threePLImportService.saveImport(sellerNo,importNo, importProductDTOs);
		    Assertions.assertThat(importProducts);
	}
	
	
}
