package com.shinhan.sunInCloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private long sellerNo;
	private long threePLNo;
	private String businessNo;
	private String companyName;
	private String productGroup;
	private boolean isMarketing;
	private int leftContract;
}
