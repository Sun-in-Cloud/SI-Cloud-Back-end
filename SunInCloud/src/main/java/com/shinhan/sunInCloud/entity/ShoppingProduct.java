package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class ShoppingProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long exportProductNo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="product_no")
	private Product product;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="export_no")
	private Shopping shopping;
	
	@NotNull
	@Builder.Default
	private String orderStatus="상품준비중";
	
	private Timestamp exportDate;
	
	@NotNull
	private int sellingPrice;
	
	private String invoiceNo;
	
	@NotNull
	private int amount;
	
	/**
	 * ShoppingProduct -> ExportProduct
	 * 쇼핑몰 주문 상품을 출고 상품으로 가져오기 위함
	 * 
	 * @param exports
	 * @return
	 */
	public ExportProduct toExportProduct(Exports exports) {
		return ExportProduct
				.builder()
				.amount(amount)
				.exports(exports)
				.product(product)
				.sellingPrice(sellingPrice)
				.build();
	}
}
