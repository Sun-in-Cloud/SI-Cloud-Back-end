package com.shinhan.sunInCloud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportProductPostDTO {
	Long sellerNo;
	Long orderNo;
	Long importNo;
	List<ImportProductDTO> importList;
}
