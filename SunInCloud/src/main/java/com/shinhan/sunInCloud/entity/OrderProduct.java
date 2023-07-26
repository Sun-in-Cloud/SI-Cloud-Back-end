package com.shinhan.sunInCloud.entity;

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
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderProductNo;
	
	@NotNull
	@ManyToOne
	private Order order;
	
	@NotNull
	@ManyToOne
	private Product product;
	
	@NotNull
	private Integer amount;
}
