package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class ImportProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long importProductNo;
	
	@NotNull
	@ManyToOne
	private Imports imports;
	
	@NotNull
	@ManyToOne
	private Product product;
	
	@NotNull
	private Integer requestAmount;
	
	// 처음 default로 0을 줄 것인지 null을 줄 것인지?
	// 값이 기본값으로 0이 들어가지 않으면 변경해줌
	@ColumnDefault("0")
	private Integer importAmount;
}
