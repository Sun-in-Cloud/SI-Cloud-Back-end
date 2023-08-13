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
public class ChannelSalesListDTO {
	private Long totalSalesAmountThisYear;
	private Long totalSalesAmountLastYear;
	private List<ChannelSalesDTO> totalSalesThisYear;
	private List<ChannelSalesDTO> totalSalesLastYear;
}
