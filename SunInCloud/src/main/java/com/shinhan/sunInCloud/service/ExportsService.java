package com.shinhan.sunInCloud.service;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;
import com.shinhan.sunInCloud.dto.TotalSalesDTO;
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

	/**
	 * 일주일간의 일별 판매 건수 조회 메서드
	 * 데이터 조회 후 DTO로 변경
	 * @param startDate
	 * @param endDate
	 * @param sellerNo
	 * @return
	 */
	public List<NumberOfSalesDTO> getNumberOfSalesWeekly(Date startDate, Date endDate, Long sellerNo) {
		List<Object[]> counts = exportProductRepository.getDailySalesCountForWeek(startDate, endDate, sellerNo);
		List<NumberOfSalesDTO> numberOfSales = new ArrayList<>();
		for (Object[] count : counts) {
			java.sql.Date date = (java.sql.Date) count[0];
			BigInteger bi = (BigInteger) count[1];
			numberOfSales.add(NumberOfSalesDTO.builder()
					.year(date.getYear() + 1900)
					.month(date.getMonth() + 1)
					.day(date.getDate())
					.numberOfSales(bi.longValue())
					.build());
		}
		return numberOfSales;
	}
	
	/**
	 * 입력으로 주어진 년, 월에 해당하는 판매 건수 조회 메서드
	 * @param sellerNo
	 * @param year
	 * @param month
	 * @return 판매건수
	 * 작성자: 손준범
	 */
	public Long getNumberOfSalesMonthly(Long sellerNo, int year, int month) {
		return exportProductRepository.getSalesCountOfMonth(sellerNo, year, month);
	}

	/**
	 * 입력으로 주어진 년도에 해당하는 판매 건수 조회 메서드
	 * @param sellerNo
	 * @param year
	 * @return 판매건수
	 * 작성자: 손준범
	 */
	public Long getNumberOfSalesYearly(Long sellerNo, int year) {
		return exportProductRepository.getSalesCountOfYear(sellerNo, year);
	}

	/**
	 * 일주일간의 일별 매출 조회 메서드
	 * @param startDate
	 * @param endDate
	 * @param sellerNo
	 * @return 7일간의 일별 매출 List
	 */
	public List<TotalSalesDTO> getTotalSalesWeekly(Date startDate, Date endDate, Long sellerNo) {
		List<Object[]> totalSales = exportProductRepository.getDailySalesForWeek(startDate, endDate, sellerNo);
		List<TotalSalesDTO> totalSalesWeekly = new ArrayList<>();
		for (Object[] totalSale : totalSales) {
			java.sql.Date date = (java.sql.Date) totalSale[0];
			Long saleAmount = ((BigDecimal)totalSale[1]).longValue();
			totalSalesWeekly.add(TotalSalesDTO.builder()
					.year(date.getYear())
					.month(date.getMonth() + 1)
					.day(date.getDate())
					.totalSales(saleAmount)
					.build());
		}
		return totalSalesWeekly;
	}

	/**
	 * 입력으로 주어진 년, 월에 해당하는 매출 조회 메서드
	 * @param sellerNo
	 * @param year
	 * @param month
	 * @return 매출
	 */
	public Long getTotalSalesMonthly(Long sellerNo, int year, int month) {
		return exportProductRepository.getMonthlySales(sellerNo, year, month);
	}

	/**
	 * 입력으로 주어진 년도에 해당하는 매출 조회 메서드
	 * @param sellerNo
	 * @param year
	 * @param month
	 * @return 매출
	 */
	public Long getTotalSalesYearly(Long sellerNo, int year) {
		return exportProductRepository.getYearlySales(sellerNo, year);
	}
}
