package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name="seller_no")
	private Seller seller;

	@NotNull
	private String productName;

	@NotNull
	private Integer safetyStock;

	@NotNull
	private Integer currentStock;

	@NotNull
	@ManyToOne
	@JoinColumn(name="detail_product_group_no")
	private DetailProductGroup detailProductGroup;

	private Integer importPrice;

	private Integer consumerPrice;

	@NotNull
	private Boolean isActive;
}
