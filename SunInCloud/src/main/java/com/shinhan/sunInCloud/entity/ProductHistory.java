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
public class ProductHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long historyProductNo;
	
	@CreationTimestamp
	private Timestamp updatedDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UpdatedType updatedType;
	
	@NotNull
	private String productNo;

	@NotNull
	private Long sellerNo;

	@NotNull
	private String productName;

	@NotNull
	private Integer safetyStock;

	@NotNull
	private Integer currentStock;
	
	@NotNull
	private Integer enoughStock;

	@NotNull
	private Long detailProductGroupNo;

	@NotNull
	private Integer importPrice;

	@NotNull
	private Integer consumerPrice;

	@NotNull
	private Boolean isActive;
}
