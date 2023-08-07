package com.shinhan.sunInCloud.marketing;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.NumberOfSalesDTO;
import com.shinhan.sunInCloud.dto.StatisticsDTO;
import com.shinhan.sunInCloud.service.MarketingService;

@SpringBootTest
public class MarketingTest {
	
	@Autowired
	MarketingService marketingService;

	@Test
	void findStatisticsBySeller() {
		StatisticsDTO statistics = marketingService.getStatisticsBySeller(8L);
		getNumberOfSalesMonthlyTest(statistics.getNumberOfSalesMonthly());
		getNumberOfSalesYearlyTest(statistics.getNumberOfSalesYearly());
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
}
