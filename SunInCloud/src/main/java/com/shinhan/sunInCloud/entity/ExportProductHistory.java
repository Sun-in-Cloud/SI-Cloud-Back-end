package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
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
public class ExportProductHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long historyExportProductNo;
	
	@CreationTimestamp
	private Timestamp updatedDate;
	
	@NotNull
	private UpdatedType updatedType;
	
	@NotNull
	private Long exportProductNo;
	
	@NotNull
	private Long exportNo;
	
	@NotNull
	private Long productNo;
	
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
