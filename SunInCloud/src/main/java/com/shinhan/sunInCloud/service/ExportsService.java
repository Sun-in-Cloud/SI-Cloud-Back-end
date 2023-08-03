package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ExportInvoiceDTO;
import com.shinhan.sunInCloud.dto.ExportProductDTO;
import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.entity.ExportProduct;
import com.shinhan.sunInCloud.entity.ExportProductHistory;
import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.Shopping;
import com.shinhan.sunInCloud.entity.ShoppingProduct;
import com.shinhan.sunInCloud.repository.ExportProductHistoryRepository;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ExportsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportsService {
	private final ExportsRepository exportsRepository;
	private final ExportProductRepository exportProductRepository;
	private final ExportProductHistoryRepository exportProductHistoryRepository;
	private final ShoppingService shoppingService;
	private final SellerService sellerService;
	private final ProductService productService;

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
		if (shoppings.size() > 0) {
			// 출고 목록 만들기
			for (Shopping shopping : shoppings) {
				exports.add(shopping.toExports(seller));
			}

			// 출고 상품 만들기
			for (Exports exp : exports) {
				List<ExportProduct> exportProducts = new ArrayList<>();
				Exports savedExports = exportsRepository.save(exp);
				List<ShoppingProduct> shoppingProducts = shoppingService
						.findShoppingProduct(savedExports.getExportNo());

				for (ShoppingProduct shoppingProduct : shoppingProducts) {
					exportProducts.add(shoppingProduct.toExportProduct(savedExports));
				}

				List<ExportProduct> savedExportProducts = exportProductRepository.saveAll(exportProducts);
				
				if(savedExports == null || savedExportProducts == null) findExports(sellerNo, 0, countPerPage);
			}
		}

		// 주문 수집하면 무조건 첫 페이지로 이동
		return findExports(sellerNo, 0, countPerPage);
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
		List<ExportsDTO> exportsDTOs = new ArrayList<>();
		Page<Exports> exports = exportsRepository.findAllBySeller_SellerNoOrderByOrderDateDesc(sellerNo,
				PageRequest.of(pageNum, countPerPage));

		for (Exports exp : exports) {
			exportsDTOs.add(exp.toExportsDTO(exportProductRepository));
		}
		return exportsDTOs;
	}

	/**
	 * 출고 목록 상세 메서드
	 * 
	 * @param exportNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public List<ExportProductDTO> exportDetail(String exportNo, int pageNum, int countPerPage) {
		List<ExportProductDTO> exportProductDTOs = new ArrayList<>();
		Page<ExportProduct> exportProducts = exportProductRepository
				.findByExports_ExportNoOrderByProduct_ProductName(exportNo, PageRequest.of(pageNum, countPerPage));

		for (ExportProduct exportProduct : exportProducts) {
			exportProductDTOs.add(exportProduct.toExportProductDTO());
		}

		return exportProductDTOs;
	}
	
	/**
	 * 현재 재고가 남은 경우에만 송장 출력하는 메서드
	 * 
	 * @param exportNo
	 * @param invoiceProducts
	 * @return
	 */
	@Transactional
	public List<ExportInvoiceDTO> printInvoice(String exportNo, List<ExportInvoiceDTO> invoiceProducts) {
		List<ExportInvoiceDTO> exportInvoiceDTOs = new ArrayList<>();
		List<ExportProduct> exportProducts = new ArrayList<>();
		String invoiceNo = String.valueOf(new Date().getTime());
		
		// 송장 출력
		for(ExportInvoiceDTO invoiceProduct : invoiceProducts) {
			int amount = invoiceProduct.getAmount();
			String productNo = invoiceProduct.getProductNo();
			ExportInvoiceDTO exportInvoiceDTO = ExportInvoiceDTO
					.builder()
					.amount(amount)
					.invoiceNo(isAvailableForExport(productNo, amount) ? invoiceNo : null)
					.productNo(invoiceProduct.getProductNo())
					.build();
			
			// 재고가 남은 경우에만 ExportProduct 업데이트 
			if(exportInvoiceDTO.getInvoiceNo() != null) {
				ExportProduct exportProduct = exportProductRepository.findByExports_ExportNoAndProduct_ProductNo(exportNo, productNo);
				ExportProductHistory exportProductHistory = exportProduct.toExportProductHistory();
				
				exportProduct.updateExportProductByExportInvoiceDTO(exportInvoiceDTO);
				exportProducts.add(exportProduct);
			}
			exportInvoiceDTOs.add(exportInvoiceDTO);
		}
		
		exportProductRepository.saveAll(exportProducts);
		
		return exportInvoiceDTOs;
	}
	
	/**
	 * 현재 재고가 주문수량보다 많은지 확인하는 메서드
	 * @param productNo
	 * @param amount
	 * @return
	 */
	private boolean isAvailableForExport (String productNo, int amount) {
		Product product = productService.findByProductNo(productNo);
		
		return amount <= product.getCurrentStock();
	}

}
