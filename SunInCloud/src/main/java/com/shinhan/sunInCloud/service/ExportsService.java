package com.shinhan.sunInCloud.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.ExportProduct;
import com.shinhan.sunInCloud.entity.Exports;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ExportsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExportsService {
	private final ExportsRepository exportsRepository;
	private final ExportProductRepository exportProductRepository;

	@Transactional
	public List<ExportProduct> register(Exports exports, List<ExportProduct> products) {
		Exports savedExports = exportsRepository.save(exports);

		for (ExportProduct product : products) {
			product.setExports(savedExports);
		}

		return exportProductRepository.saveAll(products);
	}
}
