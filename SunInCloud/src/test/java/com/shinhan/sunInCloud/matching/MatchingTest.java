package com.shinhan.sunInCloud.matching;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.MatchingSellerDTO;
import com.shinhan.sunInCloud.dto.WarehouseDTO;
import com.shinhan.sunInCloud.service.MatchingService;
import com.shinhan.sunInCloud.service.SellerService;
import com.shinhan.sunInCloud.service.WarehouseService;

@SpringBootTest
public class MatchingTest {
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private MatchingService matchingService;
	@Autowired
	private SellerService sellerService;
	
//	@Test
	public void findLeftLocation() {
		Long threePLNo = 97L;
		List<WarehouseDTO> warehouses = warehouseService.findLeftLocation(threePLNo);
		System.out.println(warehouses);
	}
	
//	@Test
	public void contract() {
		Long threePLNo = 97L;
		Long sellerNo = 9L;
		String location = "D";
		String endDate = "2023-08-18";
		MatchingDTO matchingDTO = MatchingDTO.builder()
				.endDate(endDate)
				.threePLNo(threePLNo)
				.sellerNo(sellerNo)
				.location(location)
				.build();
		
		boolean contract = matchingService.contract(matchingDTO);
		
		Assertions.assertThat(contract).isTrue();
	}
	
	@Test
	public void searchSeller() {
		String productGroup = "화장품";
		String address = "서울";
		int exportCnt = 0;
		int contractPeriod = 0;
		
		MatchingSellerDTO condition = MatchingSellerDTO
				.builder()
				.productGroup(productGroup)
				.address(address)
				.exportCnt(exportCnt)
				.contractPeriod(contractPeriod)
				.build();
		
		List<MatchingSellerDTO> sellers = matchingService.searcingByCondition(condition);
		System.out.println(sellers);
	}
}
