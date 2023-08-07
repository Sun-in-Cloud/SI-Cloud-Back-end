package com.shinhan.sunInCloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
	private Long sellerNo;
	private String productGroupName;
	private String businessNo;
	private String companyName;
	private String ceoName;
	private String address;
	private String managerName;
	private String managerPhone;
	private String managerEmail;
	private boolean isMarketing;
	
	private long sales;
	private long exportCnt;
	private MatchingDTO matching;
	
}
