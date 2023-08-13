package com.shinhan.sunInCloud.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shinhan.sunInCloud.dto.ChannelSalesDTO;
import com.shinhan.sunInCloud.dto.DangerousProductDTO;
import com.shinhan.sunInCloud.dto.ExportInvoiceDTO;
import com.shinhan.sunInCloud.dto.ExportProductDTO;
import com.shinhan.sunInCloud.dto.ExportProductListDTO;
import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.dto.ExportsListDTO;
import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.ProductSalesDTO;
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
	
	private int TOP3 = 3;
	private int TOP5 = 5;
	private int TOP10 = 10;
	
	@Value("${aws.address}")
	private String path;

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
		
		
		WebClient webClient = WebClient.create(path);
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
					.productName(invoiceProduct.getProductName())
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
	 * Map 사용
	 * 1. 각 일자별 카운트를 0으로 초기화 한
	 * 2. 데이터가 있는 날짜만 개수 변경
	 * 3. 각 개수를 DTO로 변경
	 * @param dates
	 * @param sellerNo
	 * @return NumberOfSalesDTO List
	 * 작성자: 손준범
	 */
	public List<NumberOfSalesDTO> getNumberOfSalesOfSellerWeekly(List<String> dates, Long sellerNo) {
		return getNumberOfSalesWeekly(dates, exportProductRepository.getDailySalesCountForWeek(dates, sellerNo));
	}
	
	/**
	 * 입력으로 주어진 년, 월에 해당하는 판매 건수 조회 메서드
	 * @param sellerNo
	 * @param year
	 * @param month
	 * @return 판매건수
	 * 작성자: 손준범
	 */
	public Long getNumberOfSalesOfSellerMonthly(Long sellerNo, int year, int month) {
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
	 * @param dates
	 * @return 7일간의 일별 매출 List
	 */
	public List<TotalSalesDTO> getTotalSalesWeekly(List<String> dates, Long sellerNo) {
		List<Object[]> totalSales = exportProductRepository.getDailySalesForWeek(dates, sellerNo);
		List<TotalSalesDTO> totalSalesWeekly = new ArrayList<>();
		Map<String, Long> map = aggregateWeeklyData(dates, totalSales);
		for (String date : dates) {
			String[] arr = date.split("-");
			totalSalesWeekly.add(TotalSalesDTO.builder()
					.year(Integer.parseInt(arr[0]))
					.month(Integer.parseInt(arr[1]))
					.day(Integer.parseInt(arr[2]))
					.totalSales(map.get(date))
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
	 * @return 매출
	 */
	public Long getTotalSalesYearly(Long sellerNo, int year) {
		return exportProductRepository.getYearlySales(sellerNo, year);
	}
	
	private Map<String, Long> aggregateWeeklyData(List<String> dates, List<Object[]> data) {
		Map<String, Long> map = new HashMap<>();
		for (String date : dates) {
			map.put(date, 0L);
		}
		for (Object[] oneData : data) {
			map.put(String.valueOf(oneData[0]), ((BigDecimal)oneData[1]).longValue());
		}
		return map;
	}

	/**
	 * 일주일간의 일별 판매 건수 조회 메서드
	 * Map 사용
	 * 1. 각 일자별 카운트를 0으로 초기화 한
	 * 2. 데이터가 있는 날짜만 개수 변경
	 * 3. 각 개수를 DTO로 변경
	 * @param weekDatesString
	 * @param dates
	 * @return NumberOfSalesDTO List
	 * 작성자: 손준범
	 */
	public List<NumberOfSalesDTO> getNumberOfSalesProductWeekly(List<String> dates, String productNo) {
		return getNumberOfSalesWeekly(dates, exportProductRepository.getDailySalesCountOfProductForWeek(dates, productNo));
	}
	
	/**
	 * 일주일간의 일일 판매건수
	 * @param dates
	 * @param counts
	 * @return
	 */
	private List<NumberOfSalesDTO> getNumberOfSalesWeekly(List<String> dates, List<Object[]> counts) {
		List<NumberOfSalesDTO> numberOfSales = new ArrayList<>();
		Map<String, Long> map = aggregateWeeklyData(dates, counts);
		for (String date : dates) {
			String[] arr = date.split("-");
			numberOfSales.add(NumberOfSalesDTO.builder()
					.year(Integer.parseInt(arr[0]))
					.month(Integer.parseInt(arr[1]))
					.day(Integer.parseInt(arr[2]))
					.numberOfSales(map.get(date))
					.build());
		}
		return numberOfSales;
	}
	
	/**
	 * 입력으로 주어진 년, 월에 해당하는 판매 건수 조회 메서드
	 * @param productNo
	 * @param year
	 * @param month
	 * @return 판매건수
	 * 작성자: 손준범
	 */
	
	public Long getNumberOfSalesOfProductMonthly(String productNo, int year, int month) {
		return exportProductRepository.getSalesCountOfProductOfMonth(productNo, year, month);
	}

	/**
	 * 입력으로 주어진 년도에 해당하는 판매 건수 조회 메서드
	 * @param productNo
	 * @param year
	 * @return 판매건수
	 * 작성자: 손준범
	 */
	public Long getNumberOfSalesOfProductYearly(String productNo, int year) {
		return exportProductRepository.getSalesCountOfProductOfYear(productNo, year);
	}
	
	/**
	 * 일주일간의 일별 매출 조회 메서드
	 * @param productNo
	 * @param dates
	 * @return 7일간의 일별 매출 List
	 */
	public List<TotalSalesDTO> getTotalSalesOfProductWeekly(List<String> dates, String productNo) {
		List<Object[]> totalSales = exportProductRepository.getDailySalesOfProductForWeek(dates, productNo);
		List<TotalSalesDTO> totalSalesWeekly = new ArrayList<>();
		Map<String, Long> map = aggregateWeeklyData(dates, totalSales);
		for (String date : dates) {
			String[] arr = date.split("-");
			totalSalesWeekly.add(TotalSalesDTO.builder()
					.year(Integer.parseInt(arr[0]))
					.month(Integer.parseInt(arr[1]))
					.day(Integer.parseInt(arr[2]))
					.totalSales(map.get(date))
					.build());
		}
		return totalSalesWeekly;
	}
	
	/**
	 * 입력으로 주어진 년, 월에 해당하는 매출 조회 메서드
	 * @param productNo
	 * @param year
	 * @param month
	 * @return 매출
	 */
	public Long getTotalSalesOfProductMonthly(String productNo, int year, int month) {
		return exportProductRepository.getMonthlySalesOfProduct(productNo, year, month);
	}
	
	/**
	 * 입력으로 주어진 년도에 해당하는 매출 조회 메서드
	 * @param sellerNo
	 * @param year
	 * @return 매출
	 */
	public Long getTotalSalesOfProductYearly(String productNo, int year) {
		return exportProductRepository.getYearlySalesOfProduct(productNo, year);
	}

	public List<ChannelSalesDTO> findTopChannels(Long sellerNo, int year) {
		List<ChannelSalesDTO> topChannels = exportsRepository.findTopChannels(sellerNo, year, PageRequest.of(0, TOP3));
		for (ChannelSalesDTO topChannel : topChannels) {
			topChannel.setYear(year);
		}
		return topChannels;
	}
	
	public List<ProductSalesDTO> findTopProductsOfChannel(Long sellerNo, int year, String channelName) {
		return exportsRepository.findTopProductsOfChannel(sellerNo, year, channelName, PageRequest.of(0, TOP5));
	}
	
	public Long getYearlySalesAmountOfseller(Long sellerNo, int year) {
		return exportProductRepository.getYearlySales(sellerNo, year);
	}

	/**
	 * 위험 상품 5개 조회 메서드
	 * @param sellerNo
	 * @return 발주한지 가장 오래된 상품 5개
	 */
	public List<DangerousProductDTO> getDangerousProducts(Long sellerNo) {
		return exportsRepository.getDangerousProducts(sellerNo, PageRequest.of(0, TOP10));
	}
}
