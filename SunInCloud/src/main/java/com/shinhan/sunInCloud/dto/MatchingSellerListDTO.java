package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingSellerListDTO {
	private int totalPage;
	private List<MatchingSellerDTO> matchingSellers;
}
