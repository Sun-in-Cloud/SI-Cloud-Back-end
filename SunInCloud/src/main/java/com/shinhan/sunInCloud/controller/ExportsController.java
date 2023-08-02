package com.shinhan.sunInCloud.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.ExportProductDTO;
import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.service.ExportsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExportsController {
	private final ExportsService exportsService;
	
	@GetMapping(value = {"/3pl/export/list", "/seller/export/list"})
	public List<ExportsDTO> findAllExports(Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.findExports(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping("/3pl/export/collect")
	public List<ExportsDTO> collectExports(Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.register(sellerNo, pageNum, countPerPage);
	}
	
	@GetMapping(value = {"/3pl/export/{exportNo}", "/seller/export/{exportNo}"})
	public List<ExportProductDTO> exportsDetail(@PathVariable String exportNo, Long sellerNo, int pageNum, int countPerPage) {
		return exportsService.exportDetail(exportNo, pageNum, countPerPage);
	}
}
