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
	private long sellerNo;
	private String productGroup;
	private String companyName;
	private String endDate;
	
}
