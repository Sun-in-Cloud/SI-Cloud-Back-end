package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.util.TimestampUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller {

	@Id
	private Long sellerNo;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "seller_no")
	private User user;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="product_group_no")
	private ProductGroup productGroup;
	
	@NotNull
	private String businessNo;
	@NotNull
	private String companyName;
	@NotNull
	private String ceoName;
	@NotNull
	private String address;
	@NotNull
	private String managerName;
	@NotNull
	private String managerPhone;
	@NotNull
	private String managerEmail;
	@NotNull
	private Boolean isAgreed;
	@NotNull
	private Boolean isMarketing;
	
	/**
	 * Seller -> MatchingDTO
	 * 매칭 검색시 보이는 정보
	 * @param matching
	 * @return
	 */
	public MatchingDTO toMatchingSellerDTO(Matching matching) {
		return MatchingDTO
				.builder()
				.productGroup(productGroup.getGroupName())
				.companyName(companyName)
				.endDate(matching == null ? null : TimestampUtil.convertTimestampToDate(matching.getEndDate()))
				.sellerNo(sellerNo)
				.build();
	}
	
	/**
	 * Seller -> SellerDTO
	 * 상세 조회
	 * @return
	 */
	public SellerDTO toSellerDTO(MatchingDTO matchingDTO, long sales, long exportCnt) {
		return SellerDTO
				.builder()
				.sellerNo(sellerNo)
				.productGroupName(productGroup.getGroupName())
				.businessNo(businessNo)
				.companyName(companyName)
				.ceoName(ceoName)
				.address(address)
				.managerEmail(managerEmail)
				.managerName(managerName)
				.managerPhone(managerPhone)
				.isMarketing(isMarketing)
				.isAgreed(isAgreed)
				.sales(sales)
				.exportCnt(exportCnt)
				.matching(matchingDTO)
				.build();
	}
}
