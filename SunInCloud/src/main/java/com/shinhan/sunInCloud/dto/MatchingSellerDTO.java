package com.shinhan.sunInCloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingSellerDTO {
	private String productGroup;
	private String address;
	private int exportCnt;
	private int contractPeriod;
	
	private String companyName;
	private String endDate;
}
