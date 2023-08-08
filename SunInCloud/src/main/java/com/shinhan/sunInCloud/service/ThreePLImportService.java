package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreePLImportService {
	
	private final OrderRepository orderRepository;
	private final ImportsProductRepository importProductRepository;
	private final SellerService sellerService;
	private final ImportsRepository importRepository;
	private final ProductService productService;
	
	//3.입고 - 등록 패이지로 이동
	public List<ImportProductDTO> goRegister(Long importNo, int pageNum, int countPerPage) {
		   List<ImportProductDTO> importProductDTOs = new ArrayList<>();
		   List<ImportProduct> imports =  importProductRepository.findByImports_ImportNo(importNo);
		   
		   for(ImportProduct im: imports) {
			   importProductDTOs.add(ImportProductDTO.builder().productNo(im.getProduct().getProductNo())
					   .productName(im.getProduct().getProductName()).importAmount(im.getImportAmount()).
					   requestAmount(im.getRequestAmount())
					   .build());
		   }
		   
		   Long count = importProductRepository.countByImports_ImportNo(importNo);
	       Long totalPage = calculatePageCount(count, countPerPage);     
		   return importProductDTOs;
	}
	
	//4.입고 - 등록
	   public boolean saveImport(Long sellerNo, List<ImportProductDTO> importProductDTOs) {
	         // 입고 내역을 저장함 -> 화주사 번호 필요함
	         Seller seller = sellerService.findById(sellerNo);
	         Imports imports = importRepository.save(Imports.builder().seller(seller).build());
	         
	         //importAmount, importDate가 생김
	         
	         // 각 입고 상품에 입고 번호 부여 및 입고 내역과 관계 설정
	         List<ImportProduct> importProducts = new ArrayList<>();
	         for (ImportProductDTO importProductDTO : importProductDTOs) {
	            Product product = productService.findByProductNo(importProductDTO.getProductNo());
	            ImportProduct importProduct = ImportProduct.builder()
	                  .imports(imports) // 입고 내역과 관련된 입고 상품 설정
	                  //.product(product)
	                  .importAmount(importProductDTO.getImportAmount())
	                  .build();
	            importProducts.add(importProduct);
	         }

	         // 각 입고 상품 저장
	         importProductRepository.saveAll(importProducts);
	         
	         // 입고 내역과 입고 상품이 모두 저장되면 true 반환
	         boolean importSaved = imports.getImportNo() != null;
	         
	         //order테이블에 importNo저장
//	         if(importSaved) {
//	        	 orderService.saveImportNoForSeller(orderNo, imports);
//	         }
	         return importSaved;
	      }
	
	//5.입고내역 리스트 -목록 조회
	
	//6.입고내역리스트 - 상세 조회
	
	private Long calculatePageCount(Long count, int countPerPage) {
		Long totalPage = count / countPerPage;
		if (count % countPerPage > 0) {
			++totalPage;
		}
		return totalPage;
	}
	
}
