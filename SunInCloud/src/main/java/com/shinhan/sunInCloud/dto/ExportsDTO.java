package com.shinhan.sunInCloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportsDTO {
	private String exportNo;
	private Long sellerNo;
	private String salesChannel;
	private String ordererName;
	private String address;
	private String localOrderDate;
	private String orderStatus;
}
