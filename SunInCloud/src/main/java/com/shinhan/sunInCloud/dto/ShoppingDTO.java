package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingDTO {
	private Long sellerNo;
	private List<ShoppingProductDTO> orderedProducts;
}
