package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportInvoiceRequestDTO {
	private String exportNo;
	private List<ExportInvoiceDTO> invoiceProducts;
}
