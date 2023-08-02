package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ExportProductDTO {
	private String productNo;
	private String productName;
	private int amount;
	private int sellingPrice;
	private Timestamp exportDate;
	private String invoiceNo;
	private String orderStatus;
}
