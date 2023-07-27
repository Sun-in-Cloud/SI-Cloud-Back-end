package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

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
public class MatchingHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long historyMatchingNo;
	
	@CreationTimestamp
	private Timestamp updatedDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UpdatedType updatedType;
	
	@NotNull
	private Long matchingNo;
	
	@NotNull
	private Long warehouseNo;
	
	@NotNull
	private Long sellerNo;
	
	@NotNull
	private Timestamp endDate;
}
