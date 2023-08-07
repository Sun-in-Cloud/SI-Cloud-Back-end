package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "threepl")
public class ThreePL {

	@Id
	private Long threePLNo;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "threepl_no")
	private User user;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="product_group_no")
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
	private Long fee;
	@NotNull
	private Integer cntTotal;
	@NotNull
	@Builder.Default
	private Integer cntContracted = 0;
}
