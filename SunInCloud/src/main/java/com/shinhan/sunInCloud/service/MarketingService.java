package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.dto.TotalSalesDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketingService {
	
	private final ExportsService exportsService;

	/**
	 * 1. 최근 일주일의 기록 요청(판매건수, 매출)
	 *   1-1. 시작일, 끝일을 기준으로 각 날짜의 판매 건수 조회
	 *   2-2. 시작일, 끝일을 기준으로 각 날짜의 매출 조회
	 * 2. 이번달, 지난달, 작년 동월 기록(판매건수, 매출)
	 * 3. 작년, 올해 기록(판매건수, 매출)
	 * @param sellerNo
	 * 작성자: 손준범
	 */
	public StatisticsDTO getStatisticsBySeller(Long sellerNo) {
		return StatisticsDTO.builder()
				.numberOfSalesWeekly(getNumberOfSalesOfSellerWeekly(sellerNo))
				.numberOfSalesMonthly(getNumberOfSalesMonthly(sellerNo))
				.numberOfSalesYearly(getNumberOfSalesYearly(sellerNo))
				.totalSalesWeekly(getTotalSalesWeekly(sellerNo))
				.totalSalesMonthly(getTotalSalesMonthly(sellerNo))
				.totalSalesYearly(getTotalSalesYearly(sellerNo))
				.build();
	}
	
	/**
	 * 일주일간의 일별 판매 건수 조회 메서드
	 * @param sellerNo
	 * @return 7일간의 일별 판매 건수 List
	 * 작성자: 손준범
	 */
	private List<NumberOfSalesDTO> getNumberOfSalesOfSellerWeekly(Long sellerNo) {
		return exportsService.getNumberOfSalesOfSellerWeekly(getWeekDatesString(), sellerNo);
	}
	
	/**
	 * 금년, 작년 판매 건수 조회 메서드
	 * @param sellerNo
	 * @return 금년, 작년 판매 건수 List
	 * 작성자: 손준범
	 */
	private List<NumberOfSalesDTO> getNumberOfSalesYearly(Long sellerNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<NumberOfSalesDTO> numberOfSalesYearly = new ArrayList<>();
		int[] years = {year, year - 1};
		Long[] counts = new Long[2];
		for (int i = 0; i < 2; ++i) {
			counts[i] = exportsService.getNumberOfSalesYearly(sellerNo, years[i]);
			numberOfSalesYearly.add(NumberOfSalesDTO.builder()
					.year(years[i])
					.numberOfSales(counts[i])
					.build());
		}
		return numberOfSalesYearly;
	}
	
	/**
	 * 일주일간의 일별 매출 조회 메서드
	 * @param sellerNo
	 * @return 7일간의 일별 매출 List
	 * 작성자: 손준범
	 */
	private List<TotalSalesDTO> getTotalSalesWeekly(Long sellerNo) {
		return exportsService.getTotalSalesWeekly(getWeekDatesString(), sellerNo);
	}
	
	/**
	 * 당월, 전월, 작년 동월 매출 조회
	 * @param sellerNo
	 * @return 당월, 전월, 작년 동월 매출 List
	 * 작성자: 손준범
	 */
	private List<TotalSalesDTO> getTotalSalesMonthly(Long sellerNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		List<TotalSalesDTO> totalSalesMonthly = new ArrayList<>();
		int[] years = {year, year, year - 1};
		int[] months = {month + 1, month, month + 1};
		Long[] totalSales = new Long[3];
		if (month == 0) { // 당월이 1월인 경우, 전월을 전년 12월로 처리
			--years[1];
			months[1] = 12;
		}
		for (int i = 0; i < 3; ++i) {
			totalSales[i] = exportsService.getTotalSalesMonthly(sellerNo, years[i], months[i]);
			totalSalesMonthly.add(TotalSalesDTO.builder()
					.year(years[i])
					.month(months[i])
					.totalSales(totalSales[i])
					.build());
		}
		return totalSalesMonthly;
	}
	
	private List<TotalSalesDTO> getTotalSalesYearly(Long sellerNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<TotalSalesDTO> totalSalesYearly = new ArrayList<>();
		int[] years = {year, year - 1};
		Long[] totalSales = new Long[2];
		for (int i = 0; i < 2; ++i) {
			totalSales[i] = exportsService.getTotalSalesYearly(sellerNo, years[i]);
			totalSalesYearly.add(TotalSalesDTO.builder()
					.year(years[i])
					.totalSales(totalSales[i])
					.build());
		}
		return totalSalesYearly;
	}
	
	/**
	 * 최근 일주일의 날짜 String을 추출하는 메소드 분리
	 * @return 최근7일간의 각 일자
	 * 작성자: 손준범
	 */
	private List<String> getWeekDatesString() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 1900);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -6);
		List<String> dates = new ArrayList<>();
		for (int i = 0; i < 7; ++i) {
			int month = calendar.getTime().getMonth();
			int date = calendar.getTime().getDate();
			dates.add(new StringBuilder().append(calendar.getTime().getYear())
					.append("-")
					.append(month < 10 ? new StringBuilder().append("0").append(month) : month)
					.append("-")
					.append(date < 10 ? new StringBuilder().append("0").append(date) : date)
					.toString());
			calendar.add(Calendar.DATE, 1);
		}
		return dates;
	}

	/**
	 * 특정 상품에 대한 통계
	 * 1. 최근 일주일의 기록 요청(판매건수, 매출)
	 *   1-1. 시작일, 끝일을 기준으로 각 날짜의 판매 건수 조회
	 *   2-2. 시작일, 끝일을 기준으로 각 날짜의 매출 조회
	 * 2. 이번달, 지난달, 작년 동월 기록(판매건수, 매출)
	 * 3. 작년, 올해 기록(판매건수, 매출)
	 * @param sellerNo
	 * 작성자: 손준범
	 */
	public StatisticsDTO getStatisticsOfProduct(String productNo) {
		return StatisticsDTO.builder()
				.numberOfSalesWeekly(getNumberOfSalesOfProductWeekly(productNo))
				.numberOfSalesMonthly(getNumberOfSalesMonthly(productNo))
				.numberOfSalesYearly(getNumberOfSalesOfProductYearly(productNo))
				.totalSalesWeekly(getTotalSalesOfProductWeekly(productNo))
				.totalSalesMonthly(getTotalSalesOfProductMonthly(productNo))
				.totalSalesYearly(getTotalSalesOfProductYearly(productNo))
				.build();
	}
	
	/**
	 * 일주일간의 일별 판매 건수 조회 메서드
	 * @param productNo
	 * @return 해당 상품의 7일간의 일별 판매 건수 List
	 * 작성자: 손준범
	 */
	private List<NumberOfSalesDTO> getNumberOfSalesOfProductWeekly(String productNo) {
		return exportsService.getNumberOfSalesProductWeekly(getWeekDatesString(), productNo);
	}
	
	/**
	 * 금년, 작년 판매 건수 조회 메서드
	 * @param productNo
	 * @return 금년, 작년 판매 건수 List
	 * 작성자: 손준범
	 */
	private List<NumberOfSalesDTO> getNumberOfSalesOfProductYearly(String productNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<NumberOfSalesDTO> numberOfSalesYearly = new ArrayList<>();
		int[] years = {year, year - 1};
		Long[] counts = new Long[2];
		for (int i = 0; i < 2; ++i) {
			counts[i] = exportsService.getNumberOfSalesOfProductYearly(productNo, years[i]);
			numberOfSalesYearly.add(NumberOfSalesDTO.builder()
					.year(years[i])
					.numberOfSales(counts[i])
					.build());
		}
		return numberOfSalesYearly;
	}
	
	/**
	 * 일주일간의 일별 매출 조회 메서드
	 * @param productNo
	 * @return 7일간의 일별 매출 List
	 * 작성자: 손준범
	 */
	private List<TotalSalesDTO> getTotalSalesOfProductWeekly(String productNo) {
		return exportsService.getTotalSalesOfProductWeekly(getWeekDatesString(), productNo);
	}
	
	/**
	 * 당월, 전월, 작년 동월 매출 조회
	 * @param productNo
	 * @return 당월, 전월, 작년 동월 매출 List
	 * 작성자: 손준범
	 */
	private List<TotalSalesDTO> getTotalSalesOfProductMonthly(String productNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		List<TotalSalesDTO> totalSalesMonthly = new ArrayList<>();
		int[] years = {year, year, year - 1};
		int[] months = {month + 1, month, month + 1};
		Long[] totalSales = new Long[3];
		if (month == 0) { // 당월이 1월인 경우, 전월을 전년 12월로 처리
			--years[1];
			months[1] = 12;
		}
		for (int i = 0; i < 3; ++i) {
			totalSales[i] = exportsService.getTotalSalesOfProductMonthly(productNo, years[i], months[i]);
			totalSalesMonthly.add(TotalSalesDTO.builder()
					.year(years[i])
					.month(months[i])
					.totalSales(totalSales[i])
					.build());
		}
		return totalSalesMonthly;
	}
	
	private List<TotalSalesDTO> getTotalSalesOfProductYearly(String productNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<TotalSalesDTO> totalSalesYearly = new ArrayList<>();
		int[] years = {year, year - 1};
		Long[] totalSales = new Long[2];
		for (int i = 0; i < 2; ++i) {
			totalSales[i] = exportsService.getTotalSalesOfProductYearly(productNo, years[i]);
			totalSalesYearly.add(TotalSalesDTO.builder()
					.year(years[i])
					.totalSales(totalSales[i])
					.build());
		}
		return totalSalesYearly;
	}
	
	/**
	 * 당월, 전월, 작년 동월 월별 판매 건수 조회
	 * T : String or Long
	 * @param id
	 * @return
	 */
	private <T> List<NumberOfSalesDTO> getNumberOfSalesMonthly(T id) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		List<NumberOfSalesDTO> numberOfSalesMonthly = new ArrayList<>();
		int[] years = {year, year, year - 1};
		int[] months = {month + 1, month, month + 1};
		Long[] counts = {0L, 0L, 0L};
		if (month == 0) { // 당월이 1월인 경우, 전월을 전년 12월로 처리
			--years[1];
			months[1] = 12;
		}
		for (int i = 0; i < 3; ++i) {
			if (id instanceof Long){
				counts[i] = exportsService.getNumberOfSalesOfSellerMonthly((Long)id, years[i], months[i]);
			} else if (id instanceof String) {
				counts[i] = exportsService.getNumberOfSalesOfProductMonthly((String)id, years[i], months[i]);
			} 
			numberOfSalesMonthly.add(NumberOfSalesDTO.builder()
					.year(years[i])
					.month(months[i])
					.numberOfSales(counts[i])
					.build());
		}
		return numberOfSalesMonthly;
	}
}
