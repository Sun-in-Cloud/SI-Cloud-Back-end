package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportProductListDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.dto.OrderDTO;
import com.shinhan.sunInCloud.dto.OrderListDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Order;
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
	private final OrderService orderService;
	
	/**
	 * 1.입고 예정 리스트 등록
	 * 1.1 발주 조회->목록
	 * @param sellerNo
	 * @return orders의 발주번호, 발주일자
	 */
		
	/**
	 * //1.2 발주 번호로 상세 조회
	 * @param orderNo
	 * @return 바코드 번호, 상품 이름, 발주 수량
	 */
//		public List<OrderProductDTO> findByOrderNo(Long orderNo) {
//			List<OrderProductDTO> orderDTOs = new ArrayList<>();
//			List<OrderProduct> order = orderProductRepository.findByOrder_OrderNo(orderNo);
//			//entity를 dto로 변환하기	
//			for(OrderProduct orders: order) {
//				orderDTOs.add(OrderProductDTO.builder().productNo(orders.getProduct().getProductNo())
//						.amount(orders.getAmount())
//						.productName(orders.getProduct().getProductName())
//						.build());
//			}
//			
//			return orderDTOs;
//			
//		}
	
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
		 * @return true/false
		 */
	
		public boolean saveImport(Long sellerNo, List<ImportProductDTO> importProductDTOs) {
	         // 입고 내역을 저장함 -> 화주사 번호 필요함
	         Seller seller = sellerService.findById(sellerNo);
	         Imports imports = importRepository.save(Imports.builder().seller(seller).build());
	         // 각 입고 상품에 입고 번호 부여 및 입고 내역과 관계 설정
	         List<ImportProduct> importProducts = new ArrayList<>();
	         for (ImportProductDTO importProductDTO : importProductDTOs) {
	            Product product = productService.findByProductNo(importProductDTO.getProductNo());
	            ImportProduct importProduct = ImportProduct.builder()
	                  .importAmount(importProductDTO.getImportAmount())
	                  .imports(imports) // 입고 내역과 관련된 입고 상품 설정
	                  .product(product)
	                  .importProductNo(importProductDTO.getImportProductNo())
	                  .requestAmount(importProductDTO.getRequestAmount())
	                  .build();
	            importProducts.add(importProduct);
	         }

	         // 각 입고 상품 저장
	         importProductRepository.saveAll(importProducts);

	         // 입고 내역과 입고 상품이 모두 저장되면 true 반환
	         return imports.getImportNo() != null;
	      }

		/**3.입고 예정 리스트
		 * 3.1 입고 예정 리스트 목록 조회
		 * @param importDTO
		 * @param sellerNo
		 * @param pageNumber
		 * @param pageSize
		 * @return
		 */
		public ImportProductListDTO seePreList(Long sellerNo, int pageNumber, int countPerPage) {
			List<ImportsDTO> importsDTOs = new ArrayList<>();
			Page<Imports> im = importRepository.findBySeller_SellerNo(sellerNo, PageRequest.of(pageNumber, countPerPage));
			for(Imports imports : im) {
				importsDTOs.add(ImportsDTO.builder().importDate(imports.getImportDate())
						.importNo(imports.getImportNo())
						.requestDate(imports.getRequestDate())
						.sellerNo(imports.getSeller().getSellerNo())
						.build());
			}	
			
			Long count = orderRepository.countBySeller_SellerNo(sellerNo);
			Long totalPage = calculatePageCount(count, countPerPage);
			return ImportProductListDTO.builder().totalPage(totalPage).importproduct(importsDTOs).build();
		}
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
		
		private Long calculatePageCount(Long count, int countPerPage) {
			Long totalPage = count / countPerPage;
			if (count % countPerPage > 0) {
				++totalPage;
			}
			return totalPage;
		}


}

