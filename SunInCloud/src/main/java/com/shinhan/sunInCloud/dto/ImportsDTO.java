package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImportsDTO {
	private Long importNo;
	private Long sellerNo;
	private Timestamp requestDate;
	private Timestamp importDate;
	private String localRequestDate;
	
	public Imports toImport(Seller seller) {
		return Imports.builder()
				.importNo(importNo)
				.seller(seller)
				.importDate(importDate)
				.importNo(importNo)
				.requestDate(requestDate)
				.build();
	}
}
