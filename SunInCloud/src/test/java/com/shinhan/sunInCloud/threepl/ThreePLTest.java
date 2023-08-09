package com.shinhan.sunInCloud.threepl;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.UserType;
import com.shinhan.sunInCloud.service.ProductGroupService;
import com.shinhan.sunInCloud.service.ThreePLService;

@SpringBootTest
public class ThreePLTest {

	@Autowired
	ThreePLService threePLService;
	
	@Autowired
	ProductGroupService productGroupService;
	
//	@Test
	void registerThreePL() {
		User user = User.builder()
				.loginId("oliveZero")
				.loginPassword("1234")
				.userType(UserType.THREE_PL)
				.build();
		
		ProductGroup productGroup = productGroupService.findByGroupName("화장품");
		
		ThreePL threePL = ThreePL.builder()
				.user(user)
				.address("서울특별시 마포구 월드컵북로 4길 77")
				.businessNo("277-27-01528")
				.ceoName("이선정")
				.companyName("올리브제로")
				.managerEmail("olivezero@email.com")
				.managerName("김서아")
				.managerPhone("010-0000-1234")
				.fee(100000L)
				.cntTotal(6)
				.cntContracted(0)
				.productGroup(productGroup)
				.build();
		
		ThreePL registeredThreePL = threePLService.register(threePL);
		Assertions.assertThat(threePL.getCompanyName()).isEqualTo(registeredThreePL.getCompanyName());
		Assertions.assertThat(threePL.getAddress()).isEqualTo(registeredThreePL.getAddress());
		Assertions.assertThat(registeredThreePL.getThreePLNo()).isNotNull();
	}
	
//	@Test
	public void detailThreepl() {
		Long threePLNo = 10L;
		
		ThreePLDTO threePLDTO = threePLService.threePLDetail(threePLNo);
		Assertions.assertThat(threePLDTO).isNotNull();
	}
	
	@Test
	public void matchingSellerList() {
		Long threePLNo = 10L;
		List<MatchingDTO> matchingDTOs = threePLService.findByContractedSeller(threePLNo);
		
		Assertions.assertThat(matchingDTOs.size()).isNotZero();
	}
}
