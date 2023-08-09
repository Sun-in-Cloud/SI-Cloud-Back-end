package com.shinhan.sunInCloud.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sunInCloud.dto.LoginRequestDTO;
import com.shinhan.sunInCloud.dto.LoginResponseDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.service.AuthService;

@SpringBootTest
public class AuthTest {
	@Autowired
	AuthService authService;
	
	@Test
	public void register3PL() {
		ThreePLDTO threePL = ThreePLDTO
				.builder()
				.address("서울특별시 중구 남대문로10길 29")
				.businessNo("110-81-34825")
				.ceoName("유동욱")
				.cntTotal(5)
				.companyName("신한DS")
				.fee(50000L)
				.loginId("shinhanDS")
				.loginPassword("1234")
				.managerEmail("shihanDS@gmail.com")
				.managerName("홍길동")
				.managerPhone("010-1111-1111")
				.productGroupName("전자제품")
				.build();
		
		boolean result = authService.register3PL(threePL);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void registerSeller() {
		SellerDTO seller = SellerDTO
				.builder()
				.address("서울특별시 중구 남대문로10길 29")
				.businessNo("110-81-3482765")
				.ceoName("유동욱")
				.companyName("LG전자")
				.loginId("test123")
				.loginPassword("1234")
				.managerEmail("shihanDS@gmail.com")
				.managerName("홍길동")
				.managerPhone("010-1111-1111")
				.productGroupName("전자제품")
				.isMarketing(true)
				.build();
		
		boolean result = authService.registerSeller(seller);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void login() {
		String threePLId = "oliveZero";
		String sellerId = "samsung";
		String wmsId = "admin";
		String incorrectId = "idididid";
		String password = "1234";
		
		LoginResponseDTO threePL = authService.login(new LoginRequestDTO().builder().loginId(threePLId).loginPassword(password).build());
		LoginResponseDTO seller = authService.login(new LoginRequestDTO().builder().loginId(sellerId).loginPassword(password).build());
		LoginResponseDTO wms = authService.login(new LoginRequestDTO().builder().loginId(wmsId).loginPassword(password).build());
		LoginResponseDTO fail = authService.login(new LoginRequestDTO().builder().loginId(incorrectId).loginPassword(password).build());
		
		Assertions.assertThat(threePL).isNotNull();
		Assertions.assertThat(seller).isNotNull();
		Assertions.assertThat(wms).isNotNull();
		Assertions.assertThat(fail).isNull();
	}
}
