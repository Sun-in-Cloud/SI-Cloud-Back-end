package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Shopping {
	
	@Id
	private String exportNo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="seller_no")
	private Seller seller;
	
	@NotNull
	private String salesChannel;
	
	@NotNull
	private String orderName;
	
	@NotNull
	private String address;
	
	@CreationTimestamp
	private Timestamp orderDate;
	
	@Builder.Default
	private Boolean isCollected = false;
	
	/**
	 * Shopping -> Exports
	 * 쇼핑몰 주문 목록을 출고 목록으로 가져오기 위함
	 * 
	 * @param seller
	 * @return
	 */
	public Exports toExports(Seller seller) {
		return Exports
				.builder()
				.address(address)
				.exportNo(exportNo)
				.orderDate(orderDate)
				.ordererName(orderName)
				.salesChannel(salesChannel)
				.seller(seller)
				.build();
	}
}
