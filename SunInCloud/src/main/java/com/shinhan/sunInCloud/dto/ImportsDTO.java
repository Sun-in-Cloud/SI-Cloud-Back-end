package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Seller;

public class ImportsDTO {
	private Long importNo;
	private Long sellerNo;
	private Timestamp requestDate;
	private Timestamp importDate;
	
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
