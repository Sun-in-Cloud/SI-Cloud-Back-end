package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportProductDTO {
	private String productNo;
	private String productName;
	private int amount;
	private int sellingPrice;
	private Timestamp exportDate;
	private String invoiceNo;
	private String orderStatus;
}
