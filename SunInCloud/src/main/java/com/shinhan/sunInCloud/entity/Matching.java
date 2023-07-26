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
public class Matching {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long matchingNo;
	
	@NotNull
	@ManyToOne
	private Warehouse warehouse;
	
	@NotNull
	@ManyToOne
	private Seller seller;
	
	@NotNull
	private Timestamp endDate;
}
