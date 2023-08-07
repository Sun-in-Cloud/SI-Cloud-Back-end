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
public class ExportProductListDTO {
	private int totalPage;
	private List<ExportProductDTO> exportProducts;
}
