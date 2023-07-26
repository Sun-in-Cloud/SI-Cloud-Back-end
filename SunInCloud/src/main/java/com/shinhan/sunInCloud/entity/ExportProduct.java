package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class ExportProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long exportProductNo;
	
	@NotNull
	@ManyToOne
	private Exports exports;
	
	@NotNull
	@ManyToOne
	private Product product;
	
	@NotNull
	private Integer amount;
	
	@NotNull
	private String status;
	
	@NotNull
	private Timestamp exportDate;
	
	@NotNull
	private Integer sellinPrice;
	
	@NotNull
	private String invoiceNo;
}
