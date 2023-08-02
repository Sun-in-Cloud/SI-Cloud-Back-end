package com.shinhan.sunInCloud.exports;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.service.ExportsService;

@SpringBootTest
public class ExportsTest {
	@Autowired
	private ExportsService exportsService;

	@Test
	void registerExport() {
		Long sellerNo = 8L;
		List<ExportsDTO> exports = exportsService.register(sellerNo, 1, 10);
		
		System.out.println(exports.size());
	}
	
//	@Test
	void findAllExport() {
		Long sellerNo = 8L;
		List<ExportsDTO> exports = exportsService.findExports(sellerNo, 1, 10);
		
		Assertions.assertThat(exports.size());
		System.out.println(exports.size());
	}
}
