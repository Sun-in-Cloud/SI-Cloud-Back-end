package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
public class WMS {

	@Id
	private Long wmsNo;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "wms_no")
	private User user;
	
	@NotNull
	private String name;
	@NotNull
	private String phone;
	@NotNull
	private String email;
}
