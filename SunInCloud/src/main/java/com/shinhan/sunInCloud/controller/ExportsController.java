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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "export", description = "Export API Document")
public class ExportsController {
	private final ExportsService exportsService;
	
	@GetMapping(value = {"/3pl/export/list", "/seller/export/list"})
	@Operation(summary = "findAllExports", description = "find all exports of the seller which is searched by the given id")
	public ExportsListDTO findAllExports(Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.findExports(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping("/3pl/export/collect")
	@Operation(summary = "collectExports", description = "collect all exports of the seller which is searched by the given id")
	public ExportsListDTO collectExports(Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.register(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping(value = {"/3pl/export/{exportNo}", "/seller/export/{exportNo}"})
	@Operation(summary = "exportsDetail", description = "find the detail of the export of the seller which is searched by the given id")
	public ExportProductListDTO exportsDetail(@PathVariable String exportNo, Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.exportDetail(exportNo, pageNum, countPerPage);
	}
	
	@PutMapping("/3pl/export/invoice")
	@Operation(summary = "printInvoice", description = "print the invoice of the given exports")
	public List<ExportInvoiceDTO> printInvoice(@RequestBody ExportInvoiceRequestDTO exportInvoiceRequestDTO) {
		return exportsService.printInvoice(exportInvoiceRequestDTO.getExportNo(), exportInvoiceRequestDTO.getInvoiceProducts());
	}
}
