package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

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
public class ExportProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long exportProductNo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="export_no")
	private Exports exports;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="product_no")
	private Product product;
	
	@NotNull
	private Integer amount;
	
	@ColumnDefault(value = "출고대기")
	@Builder.Default
	private String orderStatus = "출고대기";

	private Timestamp exportDate;
	
	@NotNull
	private Integer sellingPrice;

	private String invoiceNo;
}
