package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticsDTO {

	private List<NumberOfSalesDTO> numberOfSalesWeekly;
	private List<TotalSalesDTO> totalSalesWeekly;
	private List<NumberOfSalesDTO> numberOfSalesMonthly;
	private List<TotalSalesDTO> totalSalesMonthly;
	private List<NumberOfSalesDTO> numberOfSalesYearly;
	private List<TotalSalesDTO> totalSalesYearly;
}
