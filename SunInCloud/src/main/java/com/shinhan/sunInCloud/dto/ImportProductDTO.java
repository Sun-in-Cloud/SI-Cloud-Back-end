package com.shinhan.sunInCloud.dto;

import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.entity.Product;

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
