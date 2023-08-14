package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.shinhan.sunInCloud.dto.MatchingDTO;
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
public class Matching {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long matchingNo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="warehouse_no")
	private Warehouse warehouse;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="seller_no")
	private Seller seller;
	
	@NotNull
	private Timestamp endDate;
	
	/**
	 * Matching -> MatchingDTO
	 * 매칭 정보를 계약 화주사 목록으로 변환
	 * @return
	 */
	public MatchingDTO toMatchingDTO() {
		return MatchingDTO
				.builder()
				.businessNo(seller.getBusinessNo())
				.companyName(seller.getCompanyName())
				.productGroup(seller.getProductGroup().getGroupName())
				.location(warehouse.getLocation())
				.endDate(TimestampUtil.convertTimestampToDate(endDate))
				.build();
	}
}
