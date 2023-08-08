package com.shinhan.sunInCloud.dto;

import java.util.List;

import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThreePLDTO {
	private long threePLNo;
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
	private long fee;
	private int cntTotal;
	private int cntContracted;
	private UserType userType;
	
	private String endDate;
	private int leftContract;
	private long exportCnt;
	private List<MatchingDTO> matchings;
	
	/**
	 * ThreePLDTO -> User
	 * 회원가입 정보 전달하기 위함
	 * @return
	 */
	public User toUser() {
		return User
				.builder()
				.loginId(loginId)
				.loginPassword(loginPassword)
				.userType(UserType.THREE_PL)
				.build();
	}
	
	/**
	 * ThreePLDTO -> threePL
	 * 회원가입 정보 전달하기 위함
	 * @param productGroup
	 * @param user
	 * @return
	 */
	public ThreePL toThreePL(ProductGroup productGroup, User user) {
		return ThreePL
				.builder()
				.address(address)
				.businessNo(businessNo)
				.ceoName(ceoName)
				.cntContracted(cntContracted)
				.cntTotal(cntTotal)
				.companyName(companyName)
				.fee(fee)
				.managerEmail(managerEmail)
				.managerName(managerName)
				.managerPhone(managerPhone)
				.productGroup(productGroup)
				.user(user)
				.build();
	}
}
