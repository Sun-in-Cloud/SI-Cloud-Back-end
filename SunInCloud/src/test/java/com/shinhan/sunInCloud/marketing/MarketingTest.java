package com.shinhan.sunInCloud.marketing;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.ChannelSalesDTO;
import com.shinhan.sunInCloud.dto.ChannelSalesListDTO;
import com.shinhan.sunInCloud.dto.DangerousProductDTO;
import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.dto.TotalSalesDTO;
import com.shinhan.sunInCloud.service.MarketingService;

@SpringBootTest
public class MarketingTest {
	
	@Autowired
	MarketingService marketingService;

	@Test
	void findStatisticsBySeller() {
		StatisticsDTO statistics = marketingService.getStatistics(8L);
		getNumberOfSalesMonthlyTest(statistics.getNumberOfSalesMonthly());
		getNumberOfSalesYearlyTest(statistics.getNumberOfSalesYearly());
		getTotalSalesMonthlyTest(statistics.getTotalSalesMonthly());
		getTotalSalesYearlyTest(statistics.getTotalSalesYearly());
	}
	
	void getNumberOfSalesMonthlyTest(List<NumberOfSalesDTO> numberOfSalesMonthly) {
		for (NumberOfSalesDTO numberOfSales : numberOfSalesMonthly) {
			int year = numberOfSales.getYear(); 
			int month = numberOfSales.getMonth();
			Long count = numberOfSales.getNumberOfSales();
			if (year == 2023) { // 당월, 전월
				if (month == 8) { // 당월
					Assertions.assertThat(count).isEqualTo(5L);
				} else { // 전월
					Assertions.assertThat(count).isEqualTo(0L);
				}
			} else {
				Assertions.assertThat(count).isEqualTo(0L);
			}
		}
	}
	
	void getNumberOfSalesYearlyTest(List<NumberOfSalesDTO> numberOfSalesYearly) {
		for (NumberOfSalesDTO numberOfSales : numberOfSalesYearly) {
			int year = numberOfSales.getYear(); 
			Long count = numberOfSales.getNumberOfSales();
			if (year == 2023) { // 금년
				Assertions.assertThat(count).isEqualTo(5L);
			} else {
				Assertions.assertThat(count).isEqualTo(0L);
			}
		}
	}
	
	void getTotalSalesMonthlyTest(List<TotalSalesDTO> totalSalesMonthly) {
		for (TotalSalesDTO totalSales : totalSalesMonthly) {
			int year = totalSales.getYear(); 
			int month = totalSales.getMonth();
			Long sales = totalSales.getTotalSales();
			if (year == 2023) { // 금년
				if (month == 8) { // 당월
					Assertions.assertThat(sales).isEqualTo(174360L);
				} else { // 전월
					Assertions.assertThat(sales).isEqualTo(0L);
				}
			} else { // 작년 동월
				Assertions.assertThat(sales).isEqualTo(0L);
			}
		}
	}
	
	void getTotalSalesYearlyTest(List<TotalSalesDTO> totalSalesMonthly) {
		for (TotalSalesDTO totalSales : totalSalesMonthly) {
			int year = totalSales.getYear(); 
			Long sales = totalSales.getTotalSales();
			if (year == 2023) { // 금년
				Assertions.assertThat(sales).isEqualTo(174360L);
			} else { // 작년 동월
				Assertions.assertThat(sales).isEqualTo(0L);
			}
		}
	}
	
	@Test
	void findSalesOfChannels() {
		Long sellerNo = 8L;
		ChannelSalesListDTO channelSalesDTO = marketingService.getTotalSalesOfChannelsBySeller(sellerNo);
		List<ChannelSalesDTO> thisYearSales = channelSalesDTO.getTotalSalesThisYear();
		List<ChannelSalesDTO> lastYearSales = channelSalesDTO.getTotalSalesLastYear();
		Assertions.assertThat(thisYearSales.size()).isEqualTo(1);
		Assertions.assertThat(lastYearSales.size()).isEqualTo(0);
		Assertions.assertThat(thisYearSales.get(0).getChannelName()).isEqualTo("11번가");
		Assertions.assertThat(thisYearSales.get(0).getTopSalesProducts().size()).isEqualTo(5);
		Assertions.assertThat(thisYearSales.get(0).getTotalSales()).isEqualTo(290380L);
	}
	
	@Test
	void findDangerousProducts() {
		Long sellerNo = 8L;
		List<DangerousProductDTO> dangerousProducts = marketingService.getDangerousProducts(sellerNo);
		Assertions.assertThat(dangerousProducts.size()).isEqualTo(5);
	}
}
