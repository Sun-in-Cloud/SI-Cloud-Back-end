package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shinhan.sunInCloud.dto.ExportInvoiceDTO;
import com.shinhan.sunInCloud.dto.ExportProductDTO;
import com.shinhan.sunInCloud.dto.ExportProductListDTO;
import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.dto.ExportsListDTO;
import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.entity.ExportProduct;
import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ExportsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportsService {
	private final ExportsRepository exportsRepository;
	private final ExportProductRepository exportProductRepository;
	private final SellerService sellerService;
	private final ProductService productService;

	/**
	 * 쇼핑몰에 주문건 요청해 출고 목록에 추가하는 메서드
	 * @param sellerNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	@Transactional
	public ExportsListDTO register(Long sellerNo, int pageNum, int countPerPage) {
		Seller seller = sellerService.findById(sellerNo);
		
		WebClient webClient = WebClient.create("http://localhost:4885");
		List<ShoppingDTO> shoppingDTOs = webClient.get()
			.uri("/shop/order/send/" + sellerNo)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<List<ShoppingDTO>>() {})
			.block();
		
		for(ShoppingDTO shoppingDTO: shoppingDTOs) {
			List<ExportProduct> exportProducts = new ArrayList<>();
			Exports exports = shoppingDTO.toExports(seller);
			
			Exports savedExports = exportsRepository.save(exports);
			
			for(ShoppingProductDTO shoppingProductDTO : shoppingDTO.getOrderedProducts()) {
				Product product = productService.findByProductNo(shoppingProductDTO.getProductNo());
				ExportProduct exportProduct = shoppingProductDTO.toExportProduct(savedExports, product);
				
				exportProducts.add(exportProduct);
			}
			
			List<ExportProduct> savedExportProducts = exportProductRepository.saveAll(exportProducts);
			if(savedExports == null || savedExportProducts == null) findExports(sellerNo, 1, countPerPage);
			
		}
		
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
	public ExportsListDTO findExports(Long sellerNo, int pageNum, int countPerPage) {
		List<ExportsDTO> exportsDTOs = new ArrayList<>();
		Page<Exports> exports = exportsRepository.findAllBySeller_SellerNoOrderByOrderDateDesc(sellerNo,
				PageRequest.of(pageNum - 1, countPerPage));

		for (Exports exp : exports) {
			exportsDTOs.add(exp.toExportsDTO(exportProductRepository));
		}
		
		ExportsListDTO exportsListDTO = ExportsListDTO
				.builder()
				.totalPage(exports.getTotalPages())
				.exports(exportsDTOs)
				.build();
		
		return exportsListDTO;
	}

	/**
	 * 출고 목록 상세 메서드
	 * 
	 * @param exportNo
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public ExportProductListDTO exportDetail(String exportNo, int pageNum, int countPerPage) {
		List<ExportProductDTO> exportProductDTOs = new ArrayList<>();
		Page<ExportProduct> exportProducts = exportProductRepository
				.findByExports_ExportNoOrderByProduct_ProductName(exportNo, PageRequest.of(pageNum - 1, countPerPage));

		for (ExportProduct exportProduct : exportProducts) {
			exportProductDTOs.add(exportProduct.toExportProductDTO());
		}
		
		ExportProductListDTO exportProductListDTO = ExportProductListDTO
				.builder()
				.totalPage(exportProducts.getTotalPages())
				.exportProducts(exportProductDTOs)
				.build();

		return exportProductListDTO;
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
