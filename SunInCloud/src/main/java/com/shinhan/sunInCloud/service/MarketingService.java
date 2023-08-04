package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;

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
	 */
	public StatisticsDTO getStatisticsBySeller(Long sellerNo) {
		return StatisticsDTO.builder()
				.numberOfSalesWeekly(getNumberOfSalesWeekly(sellerNo))
				.numberOfSalesMonthly(getNumberOfSalesMonthly(sellerNo))
				.build();
	}
	
	private List<NumberOfSalesDTO> getNumberOfSalesWeekly(Long sellerNo) {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		Date startDay = calendar.getTime();
		return exportsService.getNumberOfSales(startDay, today, sellerNo);
	}
	
	private List<NumberOfSalesDTO> getNumberOfSalesMonthly(Long sellerNo) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		List<NumberOfSalesDTO> numberOfSalesMonthly = new ArrayList<>();
		int[] years = {year, year, year - 1};
		int[] months = {month + 1, month, month + 1};
		Long[] counts = {0L, 0L, 0L};
		if (month == 1) {
			--years[1];
			months[1] += 12;
		}
		counts[0] = exportsService.getNumberOfSalesMonthly(sellerNo);
		for (int i = 0; i < 3; ++i) {
			System.out.println(new StringBuilder().append(years[i]).append(months[i]).append(counts[i]).toString());
			numberOfSalesMonthly.add(NumberOfSalesDTO.builder()
					.year(years[i])
					.month(months[i])
					.numberOfSales(counts[i])
					.build());
		}
		return numberOfSalesMonthly;
	}
}
