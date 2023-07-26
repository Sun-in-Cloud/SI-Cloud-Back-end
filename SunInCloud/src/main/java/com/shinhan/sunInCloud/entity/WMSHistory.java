package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "wms_history")
public class WMSHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "history_wms_no")
	private Long historyWMSNo;

	@NotNull
	private Long wmsNo;
	
	@CreationTimestamp
	private Timestamp updatedDate;
	
	@NotNull
	private UpdatedType updatedType;
	
	@NotNull
	private String name;
	
	@NotNull
	private String phone;
	
	@NotNull
	private String email;
}
