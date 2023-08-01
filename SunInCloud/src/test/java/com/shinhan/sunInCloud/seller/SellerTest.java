package com.shinhan.sunInCloud.seller;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.UserType;
import com.shinhan.sunInCloud.service.ProductGroupService;
import com.shinhan.sunInCloud.service.SellerService;

@SpringBootTest
@DirtiesContext
public class SellerTest {
	
	@Autowired
	SellerService sellerService;
	
	@Autowired
	ProductGroupService productGroupService;
	
	
	@Test
	void SellerInfoSave() {

		//user에 seller추가

		
		//seller에서 user를 참조하기 때문에 user를 만들고
		//seller에 저장
		
		User user = User.builder()
				.loginId("oliveZero")
				.loginPassword("1234")
				.userType(UserType.SELLER)
				.build();
		
		User user2 = User.builder()
				.loginId("min")
				.loginPassword("1234")
				.userType(UserType.SELLER)
				.build();
		
		ProductGroup productGroup = productGroupService.findByGroupName("화장품");
		
		Seller seller1=Seller.builder()
				.user(user)
				.productGroup(productGroup)
				.businessNo("135-81-05033")
				.companyName("에뛰드홈")
				.ceoName("이수연")
				.address("서울특별시 마포구 와우산로 94")
				.managerName("박서준")
				.managerPhone("010-1111-1234")
				.managerEmail("etude_master@etude.co.kr")
				.isAgreed(true)
				.isMarketing(true)
				.build();
				
		Seller seller2=Seller.builder()
				.user(user2)
				.productGroup(productGroup)
				.businessNo("106-86-68127")
				.companyName("아웃스프리")
				.ceoName("최민정")
				.address("서울특별시 용산구 한강대로 100")
				.managerName("최도윤")
				.managerPhone("010-2222-1234")
				.managerEmail("innisfree@innisfree.com")
				.isAgreed(true)
				.isMarketing(false)
				.build();
		Seller registerSeller1 = sellerService.save(seller1);
		Seller registerSeller2 = sellerService.save(seller2);
		
		Assertions.assertThat(seller1.getManagerName()).isEqualTo(registerSeller1.getManagerName());
		Assertions.assertThat(seller2.getManagerName()).isEqualTo(registerSeller2.getManagerName());
	
		
	}
	

}
