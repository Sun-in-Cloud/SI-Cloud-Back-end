package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.entity.ExportProduct;
import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.entity.ShoppingProduct;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ExportsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportsService {
	private final ExportsRepository exportsRepository;
	private final ExportProductRepository exportProductRepository;
	private final ShoppingService shoppingService;
	private final SellerService sellerService;

	/**
	 * 주문건 수집한 후 주문 목록 리턴하는 메서드
	 * 
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	@Transactional
	public List<ExportsDTO> register(Long sellerNo, int pageNum, int countPerPage) {
		List<Exports> exports = new ArrayList<>();
		List<Shopping> shoppings = shoppingService.findNotCollected(sellerNo);
		Seller seller = sellerService.findById(sellerNo);
		
		// 수집된 주문이 있는 경우에만 출고 목록 만듦
		if(shoppings.size() > 0) {
			// 출고 목록 만들기
			for(Shopping shopping: shoppings) {
				Exports exp = Exports
						.builder()
						.address(shopping.getAddress())
						.exportNo(shopping.getExportNo())
						.orderDate(shopping.getOrderDate())
						.ordererName(shopping.getOrderName())
						.salesChannel(shopping.getSalesChannel())
						.seller(seller)
						.build();
				exports.add(exp);
			}
			
			// 출고 상품 만들기
			for(Exports exp: exports) {
				List<ExportProduct> products = new ArrayList<>();
				Exports savedExport = exportsRepository.save(exp);
				List<ShoppingProduct> shopping = shoppingService.findShoppingProduct(savedExport.getExportNo());
				
				for(ShoppingProduct shop: shopping) {
					ExportProduct product = ExportProduct
							.builder()
							.amount(shop.getAmount())
							.exports(savedExport)
							.product(shop.getProduct())
							.sellingPrice(shop.getSellingPrice())
							.build();
					products.add(product);
				}
				
				List<ExportProduct> savedProduct = exportProductRepository.saveAll(products);
			}
		}
		
		// 주문 수집하면 무조건 1페이지로 이동
		return findExports(sellerNo, 1, countPerPage);
	}
	
	/**
	 * 출고 목록 조회 메서드
	 * 
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public List<ExportsDTO> findExports(Long sellerNo, int pageNum, int countPerPage) {
		List<ExportsDTO> exports = new ArrayList<>();
		Page<Exports> exps = exportsRepository.findAllBySeller_SellerNo(sellerNo, PageRequest.of(pageNum, countPerPage));
		for(Exports exp: exps) {
			ExportsDTO export = ExportsDTO
					.builder()
					.address(exp.getAddress())
					.exportNo(exp.getExportNo())
					.orderDate(exp.getOrderDate())
					.ordererName(exp.getOrdererName())
					.orderStatus(setOrderStatus(exp.getExportNo()))
					.salesChannel(exp.getSalesChannel())
					.sellerNo(sellerNo)
					.build();
			exports.add(export);
		}
		return exports;
	}
	
	/**
	 * 주문 상태 얻기 위한 메서드
	 * 주문이 취소된 경우: 주문취소
	 * 모든 물건이 출고됨: 출고완료
	 * 나머지: 준비중
	 * 
	 * @param exportNo
	 * @return
	 */
	private String setOrderStatus(String exportNo) {
		String status = "출고완료";
		
		Long cancelCnt = exportProductRepository.countByExports_ExportNoAndOrderStatus(exportNo, "주문취소");
		Long waitingCnt = exportProductRepository.countByExports_ExportNoAndOrderStatus(exportNo, "출고대기");
		
		if(cancelCnt > 0) status = "주문취소";
		else if(waitingCnt > 0) status = "준비중";
		
		return status;		
	}
}
