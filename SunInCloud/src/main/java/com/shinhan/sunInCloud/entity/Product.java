package com.shinhan.sunInCloud.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.shinhan.sunInCloud.dto.ProductDTO;

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
public class Product {

	@Id
	private String productNo;

	@NotNull
	@ManyToOne
	@JoinColumn(name="seller_no")
	private Seller seller;

	@NotNull
	private String productName;

	@NotNull
	private Integer safetyStock;

	@NotNull
	@Builder.Default
	private Integer currentStock = 0;
	
	@NotNull
	private Integer enoughStock;

	@NotNull
	@ManyToOne
	@JoinColumn(name="detail_product_group_no")
	private DetailProductGroup detailProductGroup;

	private Integer importPrice;

	private Integer consumerPrice;

	@NotNull
	private Boolean isActive;
	
	public void updateProductByProductDTO(ProductDTO productDTO) {
		productName = productDTO.getProductName();
		safetyStock = productDTO.getSafetyStock();
		currentStock = productDTO.getCurrentStock();
		enoughStock = productDTO.getEnoughStock();
		importPrice = productDTO.getImportPrice();
		consumerPrice = productDTO.getConsumerPrice();
	}
	
	public ProductDTO toProductDTO() {
		return ProductDTO.builder().productNo(productNo)
				.productGroup(detailProductGroup.getGroupName())
				.productName(productName).safetyStock(safetyStock)
				.currentStock(currentStock).enoughStock(enoughStock)
				.importPrice(importPrice).consumerPrice(consumerPrice).build();
	}
}
