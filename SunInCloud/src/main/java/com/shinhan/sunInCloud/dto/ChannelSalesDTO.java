package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChannelSalesDTO {

	private String channelName;
	private Long totalSales;
	private Integer year;
	List<ProductSalesDTO> topSalesProducts;
	
	public ChannelSalesDTO(String channelName, Long totalSales) {
		this.channelName = channelName;
		this.totalSales = totalSales;
	}
}
