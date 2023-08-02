package com.shinhan.sunInCloud.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExportInvoiceDTO {
	private String productNo;
	private int amount;
	private String invoiceNo;
}
