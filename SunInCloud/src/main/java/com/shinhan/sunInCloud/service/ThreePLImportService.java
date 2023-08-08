package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreePLImportService {
	
	private final OrderRepository orderRepository;
	private final ImportsProductRepository importProductRepository;
	
	//3.입고 - 등록 패이지로 이동
	public List<ImportProductDTO> goRegister(Long importNo, int pageNum, int countPerPage, Long sellerNo) {
		   List<ImportProductDTO> importProductDTOs = new ArrayList<>();
		   List<ImportProduct> imports = importProductRepository.findByImportNo(importNo);
		   
		   for(ImportProduct im: imports) {
			   importProductDTOs.add(ImportProductDTO.builder().productNo(im.getProduct().getProductNo())
					   .productName(im.getProduct().getProductName()).importAmount(im.getImportAmount()).
					   requestAmount(im.getRequestAmount())
					   .build());
		   }
		   
		   Long count = orderRepository.countBySeller_SellerNo(sellerNo);
	       Long totalPage = calculatePageCount(count, countPerPage);     
		   return importProductDTOs;
	}
	
	//4.입고 - 등록
	
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
