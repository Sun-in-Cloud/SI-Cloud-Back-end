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
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerImportService {
	
	private final SellerService sellerService;
	private final ProductService productService;
	private final ImportsRepository importRepository;
	private final ImportsProductRepository importProductRepository;
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
//		public Imports savePreImport(String importNo) {
//			return orderRepository.saveAll(importNo);
//		}
//		
		//3.입고 예정 리스트
		//3.1 입고 예정 리스트 목록
		public List<ImportsDTO> seePreList(ImportsDTO importDTO, Long sellerNo, int pageNumber, int pageSize) {
			//dto로 변환해줘서 반환
			List<ImportsDTO> importsDTOs = new ArrayList<>();
			Page<Imports> page = importRepository.findBySellerNo(sellerNo, PageRequest.of(pageNumber, pageSize));
			for(Imports imports : page) {
				importsDTOs.add(importDTO.builder().importDate(imports.getImportDate())
						.importNo(imports.getImportNo())
						.requestDate(imports.getRequestDate())
						.sellerNo(imports.getSeller().getSellerNo())
						.build());
			}	
			return importsDTOs;
		}
		
		//3.2 입고 예정 리스트 상세->입고 등록 후 입고가 확정된 상태이므로 실제 입고 수량을 보는것임!
		public List<ImportProductDTO> seePreDetail(ImportProductDTO importProductDTO, Long importNo) {
			List<ImportProductDTO> importsProductDTOs=new ArrayList<>();
			List<ImportProduct> im= importProductRepository.findByImportNo(importNo);
			for(ImportProduct imp: im) {
				importsProductDTOs.add(importProductDTO.builder()
						.productNo(imp.getProduct().getProductNo())
						.productName(imp.getProduct().getProductName())
						.importAmount(imp.getImportAmount())
						.build());
			}
			return importsProductDTOs;
		}
		
		//4.입고 내역 리스트
		//4.1 목록
		public List<ImportsDTO> seeList(Long sellerNo, int pageNum, int countPerPage){
			Page<Imports> imports=importRepository.findBySellerNo(sellerNo, PageRequest.of(pageNum, countPerPage));
			List<ImportsDTO> importDTO =new ArrayList<>();
			return importDTO;
		}
		
		//4.2 상세
//		public List<ImportsDTO> seeDetail(Long importNo){
//			List<ImportsDTO> imports=new ArrayList<>();
//			imports = importRepository.findByImportNo(importNo);
//			return imports;	
//		}

}
