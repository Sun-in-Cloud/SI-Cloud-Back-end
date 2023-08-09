package com.shinhan.sunInCloud.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
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
	/***
	 *창고에서 입고를 등록하게 되면 입고 상품으로 등록이 된다.
	 *실제 입고 수량과 입고 번호가 만들어짐
	 * @param sellerNo
	 * @param importProductDTOs
	 * @return
	 */
	/**
	 *  sellerNo을 가지고 있는 입고 내역 찾기 ㅇ
	//그 입고 내역의 입고 번호 찾기 ?
	//그 입고 번호로 입고 상품을 찾기 ?
	//실제 입고 수량을 설정해주기 ?
	 */
	   public boolean saveImport(Long sellerNo, Long importNo, List<ImportProductDTO> importProductDTOs) {
		   // sellerNo을 가지고 있는 입고 내역 찾기 ㅇ
	       //importNo를 가지고 있는 입고 상품들 찾기
		    Seller seller = sellerService.findById(sellerNo);
		    Imports imports = importRepository.findByImportNo(importNo);
		    //입고내역에 판매자와 입고일짜 추가해줌
		    imports = importRepository.save(Imports.builder().seller(seller).importDate(new Timestamp(System.currentTimeMillis())).build());
		    
		    //입고 상품에 실제 입고 수량 추가하기!
		    
		    
		    List<ImportProduct> importProducts = new ArrayList<>();
		    
		    //importNo를 가진 입고상품을 찾기
		    importProducts=importProductRepository.findByImports_ImportNo(importNo);
		  //입고 내역에 입고 일자 추가
	         for (ImportProductDTO importProductDTO : importProductDTOs) {
	            Product product = productService.findByProductNo(importProductDTO.getProductNo());
	            System.out.println(importProductDTO.getProductNo());
	            System.out.println(product);
	            System.out.println(imports);
	            ImportProduct importProduct = ImportProduct.builder()
	                  .imports(imports) // 입고 내역과 관련된 입고 상품 설정
	                  .product(product)
	                  .requestAmount(importProductDTO.getRequestAmount())
	                  .importAmount(importProductDTO.getImportAmount())
	                  .build();
	            importProducts.add(importProduct);
	         }
	             
	        	 
		    // 각 입고 상품 저장
		    importProductRepository.saveAll(importProducts);

		    // 입고 내역과 입고 상품이 모두 저장되면 true 반환
		    return !importProducts.isEmpty();
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
