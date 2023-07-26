package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class UserHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long historyWMSNo;
	@CreationTimestamp
	private Timestamp updatedDate;
	@NotNull
	private UpdatedType updatedType;
	@NotNull
	private Long userNo;
	@NotNull
	private String id;
	@NotNull
	private String password;
	@NotNull
	private UserType userType;
}
