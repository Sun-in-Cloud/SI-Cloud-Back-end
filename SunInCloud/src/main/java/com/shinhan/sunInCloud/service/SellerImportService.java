package com.shinhan.sunInCloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerImportService {
	
	private final ImportsRepository importRepo;
	private final OrderRepository orderRepo;
	private final ImportsProductRepository importsProductRepo;
	
	//1.입고 예정 리스트 등록
	//1.1 발주 조회->목록
	public Optional<Order> findByOrderNo(Long orderNo){
		return orderRepo.findById(orderNo);
	}
		
		//1.2 발주 조회->상세
//		public Order findByOrderProductNo(Long orderProductNo) {
//			return orderRepo.findByOrderProductNo(orderProductNo);
//		}
	//	
		//1.3 발주 조회->등록
		//public Product saveProduct(Product ) {
			
		//}
		
		//2.입고 예정 리스트 등록
		public Imports savePreImport(String importNo) {
			return orderRepo.saveAll(importNo);
		}
		
		//3.입고 예정 리스트
		//3.1 입고 예정 리스트 목록
		public Imports seePreList(Long sellerNo, int pageNumber, int pageSize) {
			return importRepo.findByImportProduct_ImportNo(sellerNo, PageRequest.of(pageNumber, pageSize));
		}
		
		//3.2 입고 예정 리스트 상세
		public List<ImportProductDTO> seePreDetail(Long ImportNo) {
			return importRepo.findByImportNo(ImportNo);
		}
		
		//4.입고 내역 리스트
		//4.1 목록
		public List<ImportsDTO> seeList(Long sellerNo, int pageNum, int countPerPage){
			return importRepo.findBySellerNo(sellerNo);
		}
		
		//4.2 상세
//		public List<ImportsDTO> seeDetail(){
//			
//		}

}
