package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.dto.ProductDTO;
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
public class Imports {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long importNo;
	
	@NotNull
	@ManyToOne
	private Seller seller;
	
	@CreationTimestamp
	private Timestamp requestDate;
	
	private Timestamp importDate;
	
	public ImportsDTO toImportsDTO() {
	    return ImportsDTO.builder()
	            .importDate(importDate)
	            .importNo(importNo)
	            .requestDate(requestDate)
	            .localRequestDate(TimestampUtil.convertTimestampToString(requestDate))
	            .localRequestDate(TimestampUtil.convertTimestampToString(importDate))
	            .sellerNo(seller.getSellerNo()).build();
	}
}
