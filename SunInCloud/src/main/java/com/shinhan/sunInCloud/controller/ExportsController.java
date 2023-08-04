package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ExportInvoiceDTO;
import com.shinhan.sunInCloud.dto.ExportInvoiceRequestDTO;
import com.shinhan.sunInCloud.dto.ExportProductListDTO;
import com.shinhan.sunInCloud.dto.ExportsListDTO;
import com.shinhan.sunInCloud.service.ExportsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExportsController {
	private final ExportsService exportsService;
	
	@GetMapping(value = {"/3pl/export/list", "/seller/export/list"})
	public ExportsListDTO findAllExports(Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.findExports(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping("/3pl/export/collect")
	public ExportsListDTO collectExports(Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.register(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping(value = {"/3pl/export/{exportNo}", "/seller/export/{exportNo}"})
	public ExportProductListDTO exportsDetail(@PathVariable String exportNo, Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.exportDetail(exportNo, pageNum, countPerPage);
	}
	
	@PutMapping("/3pl/export/invoice")
	public List<ExportInvoiceDTO> printInvoice(@RequestBody ExportInvoiceRequestDTO exportInvoiceRequestDTO) {
		return exportsService.printInvoice(exportInvoiceRequestDTO.getExportNo(), exportInvoiceRequestDTO.getInvoiceProducts());
	}
}
