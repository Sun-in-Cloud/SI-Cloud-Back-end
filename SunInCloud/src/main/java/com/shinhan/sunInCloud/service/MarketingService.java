package com.shinhan.sunInCloud.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ChannelSalesDTO;
import com.shinhan.sunInCloud.dto.ChannelSalesListDTO;
import com.shinhan.sunInCloud.dto.DangerousProductDTO;
import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.dto.TotalSalesDTO;
import com.shinhan.sunInCloud.util.TimestampUtil;

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
	 * @param id(sellerNo or productNo)
	 * 작성자: 손준범
	 */
	public <T> StatisticsDTO getStatistics(T id) {
		return StatisticsDTO.builder()
				.numberOfSalesWeekly(getNumberOfSalesWeekly(id))
				.numberOfSalesMonthly(getNumberOfSalesMonthly(id))
				.numberOfSalesYearly(getNumberOfSalesYearly(id))
				.totalSalesWeekly(getTotalSalesWeekly(id))
				.totalSalesMonthly(getTotalSalesMonthly(id))
				.totalSalesYearly(getTotalSalesYearly(id))
				.build();
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
	 * 일주일간의 일별 판매 건수 조회 메서드
	 * T : String or Long
	 * @param id
	 * @return 해당 상품의 7일간의 일별 판매 건수 List
	 * 작성자: 손준범
	 */
	private <T> List<NumberOfSalesDTO> getNumberOfSalesWeekly(T id) {
		List<NumberOfSalesDTO> numberOfSalesWeekly = new ArrayList<>();
		if (id instanceof Long) {
			numberOfSalesWeekly = exportsService.getNumberOfSalesOfSellerWeekly(getWeekDatesString(), (Long) id); 
		} else if (id instanceof String) {
			numberOfSalesWeekly = exportsService.getNumberOfSalesProductWeekly(getWeekDatesString(), (String) id); 
		}
		return numberOfSalesWeekly;
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
	
	/**
	 * 금년, 작년 판매 건수 조회 메서드
	 * T : String or Long
	 * @param id
	 * @return 금년, 작년 판매 건수 List
	 * 작성자: 손준범
	 */
	private <T> List<NumberOfSalesDTO> getNumberOfSalesYearly(T id) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<NumberOfSalesDTO> numberOfSalesYearly = new ArrayList<>();
		int[] years = {year, year - 1};
		Long[] counts = new Long[2];
		for (int i = 0; i < 2; ++i) {
			if (id instanceof Long) {
				counts[i] = exportsService.getNumberOfSalesYearly((Long) id, years[i]);
			} else if (id instanceof String) {
				counts[i] = exportsService.getNumberOfSalesOfProductYearly((String) id, years[i]);
			}
			numberOfSalesYearly.add(NumberOfSalesDTO.builder()
					.year(years[i])
					.numberOfSales(counts[i])
					.build());
		}
		return numberOfSalesYearly;
	}
	
	/**
	 * 일주일간의 일별 매출 조회 메서드
	 * T : String or Long
	 * @param id
	 * @return 7일간의 일별 매출 List
	 * 작성자: 손준범
	 */
	private <T> List<TotalSalesDTO> getTotalSalesWeekly(T id) {
		List<TotalSalesDTO> totalSalesWeekly = new ArrayList<>();
		if (id instanceof Long) {
			totalSalesWeekly = exportsService.getTotalSalesWeekly(getWeekDatesString(), (Long) id);
		} else if (id instanceof String) {
			totalSalesWeekly = exportsService.getTotalSalesOfProductWeekly(getWeekDatesString(), (String) id); 
		}
		return totalSalesWeekly; 
	}
	
	/**
	 * 당월, 전월, 작년 동월 매출 조회
	 * T : String or Long
	 * @param id
	 * @return 당월, 전월, 작년 동월 매출 List
	 * 작성자: 손준범
	 */
	private <T> List<TotalSalesDTO> getTotalSalesMonthly(T id) {
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
			if (id instanceof Long) {
				totalSales[i] = exportsService.getTotalSalesMonthly((Long) id, years[i], months[i]);				
			} else if (id instanceof String) {
				totalSales[i] = exportsService.getTotalSalesOfProductMonthly((String) id, years[i], months[i]);
			}
			totalSalesMonthly.add(TotalSalesDTO.builder()
					.year(years[i])
					.month(months[i])
					.totalSales(totalSales[i])
					.build());
		}
		return totalSalesMonthly;
	}
	
	/**
	 * 금년, 작년 매출 조회
	 * T : String or Long
	 * @param id
	 * @return
	 */
	private <T> List<TotalSalesDTO> getTotalSalesYearly(T id) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<TotalSalesDTO> totalSalesYearly = new ArrayList<>();
		int[] years = {year, year - 1};
		Long[] totalSales = new Long[2];
		for (int i = 0; i < 2; ++i) {
			if (id instanceof Long) {
				totalSales[i] = exportsService.getTotalSalesYearly((Long) id, years[i]);				
			} else if (id instanceof String) {
				totalSales[i] = exportsService.getTotalSalesOfProductYearly((String) id, years[i]);
			}
			totalSalesYearly.add(TotalSalesDTO.builder()
					.year(years[i])
					.totalSales(totalSales[i])
					.build());
		}
		return totalSalesYearly;
	}
	
	public ChannelSalesListDTO getTotalSalesOfChannelsBySeller(Long sellerNo) {
		Long thisYearSalesAmount = exportsService.getYearlySalesAmountOfseller(sellerNo, TimestampUtil.getLastYear() + 1);
		Long lastYearSalesAmount = exportsService.getYearlySalesAmountOfseller(sellerNo, TimestampUtil.getLastYear());
		List<ChannelSalesDTO> thisYearSales = getYearlySalesByChannels(sellerNo, TimestampUtil.getLastYear() + 1, thisYearSalesAmount);
		List<ChannelSalesDTO> lastYearSales = getYearlySalesByChannels(sellerNo, TimestampUtil.getLastYear(), lastYearSalesAmount);
		return ChannelSalesListDTO.builder().totalSalesAmountThisYear(thisYearSalesAmount).totalSalesThisYear(thisYearSales)
				.totalSalesAmountLastYear(lastYearSalesAmount).totalSalesLastYear(lastYearSales).build();
	}
	
	private List<ChannelSalesDTO> getYearlySalesByChannels(Long sellerNo, int year, Long yearlySalesAmount) {
		List<ChannelSalesDTO> yearlySalesOfChannels = exportsService.findTopChannels(sellerNo, year);
		for (ChannelSalesDTO channelSales : yearlySalesOfChannels) {			
			channelSales.setTopSalesProducts(exportsService.findTopProductsOfChannel(sellerNo, year, channelSales.getChannelName()));
			channelSales.setSalesPercentage(Math.round((double)channelSales.getTotalSales() / yearlySalesAmount * 10000) / 100.0);
		}
		return yearlySalesOfChannels;
	}

	/**
	 * 위험 상품 5개 조회 메서드
	 * @param sellerNo
	 * @return 발주한지 가장 오래된 상품 5개
	 */
	public List<DangerousProductDTO> getDangerousProducts(Long sellerNo) {
		List<DangerousProductDTO> dangerousProducts = exportsService.getDangerousProducts(sellerNo);
		//Random random ;
		for (DangerousProductDTO dangerousProduct : dangerousProducts) {
			dangerousProduct.setLastOrderDate(TimestampUtil.convertTimestampToString(new Timestamp(dangerousProduct.getOrderDate().getTime())));
			
			int randomDanger = new Random().nextInt(41)+40;
			dangerousProduct.setDangerDegree(randomDanger);
		}
		return dangerousProducts;
	}
}
