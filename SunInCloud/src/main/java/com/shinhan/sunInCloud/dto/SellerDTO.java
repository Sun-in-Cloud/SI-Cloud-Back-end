package com.shinhan.sunInCloud.dto;

import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
	private Long sellerNo;
	private String loginId;
	private String loginPassword;
	private String productGroupName;
	private String businessNo;
	private String companyName;
	private String ceoName;
	private String address;
	private String managerName;
	private String managerPhone;
	private String managerEmail;
	private boolean isMarketing;
	private String endDate;
	
	private long sales;
	private long exportCnt;
	private MatchingDTO matching;
	
	/**
	 * SellerDTO -> User
	 * 회원가입 정보 전달하기 위함
	 * @return
	 */
	public User toUser() {
		return User
				.builder()
				.loginId(loginId)
				.loginPassword(loginPassword)
				.userType(UserType.SELLER)
				.build();
	}
	
	public Seller toSeller(ProductGroup productGroup, User user) {
		return Seller
				.builder()
				.address(address)
				.businessNo(businessNo)
				.ceoName(ceoName)
				.companyName(companyName)
				.isMarketing(isMarketing)
				.managerEmail(managerEmail)
				.managerName(managerName)
				.managerPhone(managerPhone)
				.productGroup(productGroup)
				.user(user)
				.build();
	}
}
