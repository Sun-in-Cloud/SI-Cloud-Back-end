package com.shinhan.sunInCloud.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.dto.UserDTO;
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
@Table(name = "threepl")
public class ThreePL {

	@Id
	private Long threePLNo;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "threepl_no")
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
	private Long fee;
	@NotNull
	private Integer cntTotal;
	@NotNull
	@Builder.Default
	private Integer cntContracted = 0;
	
	/**
	 * ThreePL -> ThreePLDTO
	 * 상세 조회
	 * @param endDate
	 * @param exportCnt
	 * @param matchings
	 * @return
	 */
	public ThreePLDTO toThreePLDTO(String endDate, long exportCnt, List<MatchingDTO> matchings) {
		return ThreePLDTO
				.builder()
				.productGroupName(productGroup.getGroupName())
				.businessNo(businessNo)
				.companyName(companyName)
				.ceoName(ceoName)
				.address(address)
				.managerEmail(managerEmail)
				.managerName(managerName)
				.managerPhone(managerPhone)
				.fee(fee)
				.cntTotal(cntTotal)
				.cntContracted(cntContracted)
				.endDate(endDate)
				.leftContract(cntTotal - cntContracted)
				.exportCnt(exportCnt)
				.matchings(matchings)
				.build();
	}
	
	/**
	 * ThreePL -> MatchingDTO
	 * 매칭 검색시 보이는 정보
	 * @param endDate
	 * @return
	 */
	public MatchingDTO toMatchingThreePLDTO(String endDate) {
		return MatchingDTO
				.builder()
				.productGroup(productGroup.getGroupName())
				.companyName(companyName)
				.endDate(endDate)
				.threePLNo(threePLNo)
				.leftLocation(cntTotal - cntContracted)
				.build();
	}
	
	/**
	 * ThreePL -> UserDTO
	 * 목록 조회시 나오는 간단한 정보들
	 * @return
	 */
	public UserDTO toUserDTO() {
		return UserDTO
				.builder()
				.businessNo(businessNo)
				.companyName(companyName)
				.productGroup(productGroup.getGroupName())
				.leftContract(cntTotal - cntContracted)
				.threePLNo(threePLNo)
				.build();
	}
}
