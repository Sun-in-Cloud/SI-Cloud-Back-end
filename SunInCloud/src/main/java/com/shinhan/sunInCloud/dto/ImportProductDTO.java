package com.shinhan.sunInCloud.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Product;
import com.shinhan.sunInCloud.entity.Seller;

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
public class ImportProductDTO {
	private Long importProductNo;
	private String productNo;
	private Long importNo;
	private int requestAmount;
	private int importAmount;
	
	public ImportProduct toImportsProduct(Imports imports, Product product) {
		return ImportProduct.builder()
				.importProductNo(importProductNo)
				.product(product)
				.importAmount(importAmount)
				.importProductNo(importProductNo)
				.imports(imports)
				.build();

	}

}
