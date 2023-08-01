package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
public class ShoppingProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String exportProductNo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="product_no")
	private Product product;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="export_no")
	private Shopping shopping;
	
	@NotNull
	@Builder.Default
	private String orderStatus="상품준비중";
	
	private Timestamp exportDate;
	
	@NotNull
	private int sellingPrice;
	
	private String invoiceNo;
	
	@NotNull
	private int amount;
	
}
