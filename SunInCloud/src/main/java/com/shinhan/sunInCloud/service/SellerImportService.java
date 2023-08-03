package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.dto.OrderDTO;
import com.shinhan.sunInCloud.dto.OrderProductDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.OrderProduct;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderProductRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerImportService {
	
	private final SellerService sellerService;
	private final ProductService productService;
	private final ImportsRepository importRepository;
	private final ImportsProductRepository importProductRepository;
	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
	private final ProductRepository productRepository;
	
	/**
	 * 1.입고 예정 리스트 등록
	 * 1.1 발주 조회->목록
	 * @param sellerNo
	 * @return orders의 발주번호, 발주일자
	 */
	public List<Order> findBySellerNo(Long sellerNo){
		return orderRepository.findBySeller_SellerNo(sellerNo);
	}
	
		
	/**
	 * //1.2 발주 번호로 상세 조회
	 * @param orderNo
	 * @return 바코드 번호, 상품 이름, 발주 수량
	 */
		public List<OrderProductDTO> findByOrderNo(Long orderNo) {
			List<OrderProductDTO> orderDTOs = new ArrayList<>();
			List<OrderProduct> order = orderProductRepository.findByOrder_OrderNo(orderNo);
			//entity를 dto로 변환하기	
			for(OrderProduct orders: order) {
				orderDTOs.add(OrderProductDTO.builder().productNo(orders.getProduct().getProductNo())
						.amount(orders.getAmount())
						.productName(orders.getProduct().getProductName())
						.build());
			}
			
			return orderDTOs;
			
		}
	
		/**1.3 발주 조회->검색
		 * @param productName
		 * @return
		 */
		public List<Product> searchOrder(String productName) {
			return productRepository.findByProductName(productName);
			
		}

		/**
		 * 2.입고 예정 리스트 등록
		 * @param sellerNo
		 * @return 
		 */
//		public Imports saveImport(Long sellerNo, List<ImportProductDTO> importProductDTOs) {
//			return orderRepository.saveAll(importNo);
//		}
//		
//		
//		//3.입고 예정 리스트
//		//3.1 입고 예정 리스트 목록
//		public List<ImportsDTO> seePreList(ImportsDTO importDTO, Long sellerNo, int pageNumber, int pageSize) {
//			//dto로 변환해줘서 반환
//			List<ImportsDTO> importsDTOs = new ArrayList<>();
//			Page<Imports> page = importRepository.findBySellerNo(sellerNo, PageRequest.of(pageNumber, pageSize));
//			for(Imports imports : page) {
//				importsDTOs.add(importDTO.builder().importDate(imports.getImportDate())
//						.importNo(imports.getImportNo())
//						.requestDate(imports.getRequestDate())
//						.sellerNo(imports.getSeller().getSellerNo())
//						.build());
//			}	
//			return importsDTOs;
//		}
//		
//		//3.2 입고 예정 리스트 상세->입고 등록 후 입고가 확정된 상태이므로 실제 입고 수량을 보는것임!
//		public List<ImportProductDTO> seePreDetail(ImportProductDTO importProductDTO, Long importNo) {
//			List<ImportProductDTO> importsProductDTOs=new ArrayList<>();
//			List<ImportProduct> im= importProductRepository.findByImportNo(importNo);
//			for(ImportProduct imp: im) {
//				importsProductDTOs.add(importProductDTO.builder()
//						.productNo(imp.getProduct().getProductNo())
//						.productName(imp.getProduct().getProductName())
//						.importAmount(imp.getImportAmount())
//						.build());
//			}
//			return importsProductDTOs;
//		}
//		
//		//4.입고 내역 리스트
//		//4.1 목록
//		public List<ImportsDTO> seeList(Long sellerNo, int pageNum, int countPerPage){
//			Page<Imports> imports=importRepository.findBySellerNo(sellerNo, PageRequest.of(pageNum, countPerPage));
//			List<ImportsDTO> importDTO =new ArrayList<>();
//			return importDTO;
//		}
		
		//4.2 상세
//		public List<ImportsDTO> seeDetail(Long importNo){
//			List<ImportsDTO> imports=new ArrayList<>();
//			imports = importRepository.findByImportNo(importNo);
//			return imports;	
//		}

}
