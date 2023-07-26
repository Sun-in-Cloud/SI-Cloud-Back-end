package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
public class Product {

	@Id
	private String productNo;

	@NotNull
	@ManyToOne
	private Seller seller;

	@NotNull
	private String name;

	@NotNull
	private Integer safetyStock;

	@NotNull
	private Integer currentStock;

	@NotNull
	@ManyToOne
	private DetailProductGroup detailProductGroup;

	@NotNull
	private Integer importPrice;

	@NotNull
	private Integer consumerPrice;

	@NotNull
	private Boolean isActive;
}
