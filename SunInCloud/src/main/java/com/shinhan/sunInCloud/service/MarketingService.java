package com.shinhan.sunInCloud.service;

import java.time.LocalDate;
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
}
