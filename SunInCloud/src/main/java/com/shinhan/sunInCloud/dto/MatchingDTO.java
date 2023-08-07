package com.shinhan.sunInCloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingDTO {
	private String endDate;
	private Long sellerNo;
	private Long threePLNo;
	private String location;
	private String sellerName;
	private String productGroup;
}
