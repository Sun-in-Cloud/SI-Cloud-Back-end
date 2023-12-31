package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shinhan.sunInCloud.dto.ImportListDTO;
import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportProductListDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ImportsProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderProductRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.repository.ProductRepository;
import com.shinhan.sunInCloud.util.TimestampUtil;

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
		public List<Product> searchOrder(String productName, Long sellerNo) {
			//특정화주사를 조회하는 코드 추가
			return productRepository.findByProductNameContainingAndSellerNo(productName, sellerNo);
			
		}
		
		
		/**
		 * 2.입고 예정 리스트 등록
		 * @param sellerNo
		 * @return true/false
		 */
	//발주 등록을 시키면 발주 내역에도 importNo가 저장되고 입고 내역에도 importNo가 생김
		   
		   public boolean saveImport(Long sellerNo, Long orderNo, List<ImportProductDTO> importProductDTOs) {
		         // 입고 내역을 저장함 -> 화주사 번호 필요함
		         Seller seller = sellerService.findById(sellerNo);
		         Imports imports = importRepository.save(Imports.builder().seller(seller).build());
		         
		         //
		         
		         // 각 입고 상품에 입고 번호 부여 및 입고 내역과 관계 설정
		         List<ImportProduct> importProducts = new ArrayList<>();
		         for (ImportProductDTO importProductDTO : importProductDTOs) {
		            Product product = productService.findByProductNo(importProductDTO.getProductNo());
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
		         boolean importSaved = imports.getImportNo() != null;
		         
		         //order테이블에 importNo저장
		         if(importSaved) {
		        	 orderService.saveImportNoForSeller(orderNo, imports);
		         }
		         return importSaved;
		      }


		/**3.입고 예정 리스트
		 * 3.1 입고 예정 리스트 목록 조회
		 * @param importDTO
		 * @param sellerNo
		 * @param pageNumber
		 * @param pageSize
		 * @return
		 */
		   //내가 보여줄거 
		public List<ImportProductListDTO> seePreList(Long sellerNo, int pageNumber, int countPerPage) {
			//sellerNo가 있는 
			List<ImportsDTO> importsDTOs = new ArrayList<>();
			List<Imports> im = importRepository.findAllBySeller_SellerNo(sellerNo, PageRequest.of(pageNumber, countPerPage));
			for(Imports imports : im) {
				importsDTOs.add(ImportsDTO.builder().localImportDate(TimestampUtil.convertTimestampToString(imports.getImportDate()))
						.importNo(imports.getImportNo())
						.localRequestDate(TimestampUtil.convertTimestampToString(imports.getRequestDate()))
						.build());
			}	
			
			Long count = orderRepository.countBySeller_SellerNo(sellerNo);
			Long totalPage = calculatePageCount(count, countPerPage);

		    ImportProductListDTO importProductListDTO = ImportProductListDTO.builder()
		            .totalPage(totalPage)
		            .importproduct(importsDTOs)
		            .build();

		    return Collections.singletonList(importProductListDTO);
		}

		/**
		 * 3.2 입고 예정 리스트 상세->입고 등록 후 입고가 확정된 상태이므로 실제 입고 수량을 보는것임!
		 * @param importProductDTO
		 * @param importNo
		 * @return List<ImportProductDTO>
		 */
		public List<ImportProductDTO> seePreDetail(Long importNo) { 
			List<ImportProduct> im= importProductRepository.findByImports_ImportNo(importNo);
			List<ImportProductDTO> importProductDTOs = new ArrayList<>();
			for(ImportProduct importProduct : im) {
				importProductDTOs.add(ImportProductDTO.builder()
						.productNo(importProduct.getProduct().getProductNo())
						.productName(importProduct.getProduct().getProductName())
						.requestAmount(importProduct.getRequestAmount())
						.build());
			}
			System.out.println(importProductDTOs.size());
			return importProductDTOs;
		
		}
		
//      //4.입고 내역 리스트
//      //4.1 목록
      public ImportProductListDTO seeList(Long sellerNo, int pageNum, int countPerPage){
      //importDate가 null이 아니면 조회 가능 출고 목록 조회와 로직이 같음
         //입고 번호, 리스트 작성 일자
    	 List<ImportsDTO> importDTO =new ArrayList<>();
         Long count = importRepository.countBySeller_SellerNoAndImportDateIsNotNull(sellerNo);
         Long totalPage = calculatePageCount(count, countPerPage);
    	 List<Imports> imports = importRepository.findBySeller_SellerNoAndImportDateIsNotNullOrderByImportDateDesc(sellerNo, PageRequest.of(pageNum, countPerPage));
 
         for(Imports im: imports){
            importDTO.add(ImportsDTO.builder().importNo(im.getImportNo())
                  .localRequestDate(TimestampUtil.convertTimestampToString(im.getRequestDate()))
                  .localImportDate(TimestampUtil.convertTimestampToString(im.getImportDate()))
                  .build());
         }  
         ImportProductListDTO productListDTO = ImportProductListDTO.builder()
               .importproduct(importDTO)
               .totalPage(totalPage)
               .build();
         return  productListDTO; // 수정된 리턴 타입
      }
		
		//4.2 상세
      public List<ImportProductDTO> seeDetail(Long importNo){
    		List<ImportProduct> im= importProductRepository.findByImports_ImportNoAndImportAmountIsNotNull(importNo);
    		System.out.println(im.size());
			List<ImportProductDTO> importProductDTOs = new ArrayList<>();
			for(ImportProduct importProduct : im) {
				importProductDTOs.add(ImportProductDTO.builder()
						.productNo(importProduct.getProduct().getProductNo())
						.productName(importProduct.getProduct().getProductName())
						.importAmount(importProduct.getImportAmount())
						.build());
			}
		
			return importProductDTOs;
		}
		
		private Long calculatePageCount(Long count, int countPerPage) {
			Long totalPage = count / countPerPage;
			if (count % countPerPage > 0) {
				++totalPage;
			}
			return totalPage;
		}

	/**
	 * 입고 예정 리스트 조회
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 */
	public ImportListDTO findPreImportList(Long sellerNo, int pageNum, int countPerPage) {
		List<Imports> preImports = importRepository.findBySeller_SellerNoOrderByRequestDateDesc(sellerNo, PageRequest.of(pageNum, countPerPage));
		List<ImportsDTO> preImportDTOs = new ArrayList<>();
		for (Imports preImport : preImports) {
			preImportDTOs.add(ImportsDTO.builder()
					.importNo(preImport.getImportNo())
					.requestDate(preImport.getRequestDate())
					.localRequestDate(TimestampUtil.convertTimestampToString(preImport.getRequestDate()))
					.isImported(preImport.getImportDate() == null ? false : true)
					.build());
		}
		Long count = importRepository.countBySeller_SellerNo(sellerNo);
		Long totalPage = calculatePageCount(count, countPerPage);
		return ImportListDTO.builder().preImports(preImportDTOs).totalPage(totalPage).build();
	}
}

