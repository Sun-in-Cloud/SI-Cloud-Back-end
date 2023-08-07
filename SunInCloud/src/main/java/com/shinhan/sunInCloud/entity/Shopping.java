package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.shinhan.sunInCloud.dto.ShoppingDTO;
import com.shinhan.sunInCloud.dto.ShoppingProductDTO;

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
	 * Shopping -> ShoppingDTO
	 * 주문건 전달을 위함
	 * @param shopingProductDTOs
	 * @return
	 */
	public ShoppingDTO toShoppingDTO(List<ShoppingProductDTO> shopingProductDTOs) {
		return ShoppingDTO
				.builder()
				.sellerNo(seller.getSellerNo())
				.address(address)
				.exportNo(exportNo)
				.orderDate(orderDate)
				.ordererName(orderName)
				.salesChannel(salesChannel)
				.orderedProducts(shopingProductDTOs)
				.build();
	}
}
