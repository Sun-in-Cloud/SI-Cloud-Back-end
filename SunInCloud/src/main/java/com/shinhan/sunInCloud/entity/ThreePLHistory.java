package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "threepl_history")
public class ThreePLHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "history_threepl_no")
	private Long historyThreeplNo;
	
	@NotNull
	private Long threepl_no;
	
	@CreationTimestamp
	private Timestamp updatedDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UpdatedType updatedType;
	
	@NotNull
	private Long productGroupNo;
	
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
	private Long fee;
	
	@NotNull
	private Integer cntTotal;
	
	@NotNull
	private Integer cntContracted;
}
