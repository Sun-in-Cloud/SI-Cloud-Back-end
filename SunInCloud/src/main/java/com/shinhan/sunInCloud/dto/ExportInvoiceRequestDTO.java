package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExportInvoiceRequestDTO {
	private String exportNo;
	private List<ExportInvoiceDTO> invoiceProducts;
}
