package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

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
public class CurrentStockHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long currentStockHistoryNo;
	
	@NotNull
	private String productNo;
	
	@CreationTimestamp
	private Timestamp updatedDate;
	
	@NotNull
	private int preStock;
	
	@NotNull
	private int currentStock;
	
	@NotNull
	private int updatedCount;
}
