package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Seller {

	@Id
	private Long sellerNo;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "seller_no")
	private User user;
	
	@ManyToOne
	@NotNull
	private ProductGroup productGroup;
	
	@NotNull
	private String businessNo;
	@NotNull
	private String companyName;
	@NotNull
	private String ceoName;
	@NotNull
	private String address;
	@NotNull
	private String managerName;
	@NotNull
	private String managerPhone;
	@NotNull
	private String managerEmail;
	@NotNull
	private Boolean isAgreed;
	@NotNull
	private Boolean isMarketing;
}
