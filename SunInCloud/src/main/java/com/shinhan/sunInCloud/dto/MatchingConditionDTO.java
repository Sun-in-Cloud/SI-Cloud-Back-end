package com.shinhan.sunInCloud.dto;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingConditionDTO {
	private String productGroup;
	private String address;
	private int numValue;
	private int contractPeriod;
	
	private int pageNum;
	private int countPerPage;
}
