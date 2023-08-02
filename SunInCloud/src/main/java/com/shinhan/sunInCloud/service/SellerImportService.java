package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerImportService {
	
	private final ImportsRepository importRepository;
	private final OrderRepository orderRepository;
	
	//1.입고 예정 리스트 등록
	//1.1 발주 조회->목록
	public Optional<Order> findByOrderNo(Long orderNo){
		return orderRepository.findById(orderNo);
	}
		
		//1.2 발주 조회->상세
//		public Order findByOrderProductNo(Long orderProductNo) {
//			return orderRepo.findByOrderProductNo(orderProductNo);
//		}
	
		//1.3 발주 조회->등록
		//public Product saveProduct(Product ) {
			
		//}
		
		//2.입고 예정 리스트 등록
		public Imports savePreImport(String importNo) {
			return orderRepository.saveAll(importNo);
		}
		
		//3.입고 예정 리스트
		//3.1 입고 예정 리스트 목록
		public List<ImportsDTO> seePreList(Long sellerNo, int pageNumber, int pageSize) {
			List<Imports> imports=new ArrayList<>();
			imports= importRepository.findBySellerNo(sellerNo, PageRequest.of(pageNumber, pageSize));
			
			//dto로 변환해줘서 반환
			
			return imports;
		}
		
		//3.2 입고 예정 리스트 상세
		public List<ImportProductDTO> seePreDetail(Long importNo) {
			List<ImportProductDTO> imports=new ArrayList<>();
			imports= importRepository.findByImports_importsNo(importNo);
			return imports;
		}
		
		//4.입고 내역 리스트
		//4.1 목록
		public List<ImportsDTO> seeList(Long sellerNo, int pageNum, int countPerPage){
			Page<Imports> imports=importRepository.findBySellerNo(sellerNo, PageRequest.of(pageNum, countPerPage));
			List<ImportsDTO> importDTO =new ArrayList<>();
			return importDTO;
		}
		
		//4.2 상세
		public List<ImportsDTO> seeDetail(Long importNo){
			List<ImportsDTO> imports=new ArrayList<>();
			imports = importRepository.findByImportNo(importNo);
			return imports;	
		}

}
