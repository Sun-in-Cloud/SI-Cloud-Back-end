package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long historyOrderNo;
	
	private Timestamp orderDate;
	
	@CreationTimestamp
	private Timestamp deletedDate;
	
	private Long sellerNo;
	private String productNo;
	private Integer amount;
}
