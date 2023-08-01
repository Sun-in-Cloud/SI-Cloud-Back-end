package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

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
public class Exports {

	@Id
	private String exportNo;
	
	@NotNull
	@ManyToOne
	private Seller seller;
	
	@NotNull
	private String salesChannel;
	
	@NotNull
	private String ordererName;

	@NotNull
	private String address;
	
	@NotNull
	private Timestamp orderDate;
}
