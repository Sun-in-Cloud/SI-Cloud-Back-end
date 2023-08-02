package com.shinhan.sunInCloud.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.shinhan.sunInCloud.dto.ExportsDTO;
import com.shinhan.sunInCloud.repository.ExportProductRepository;

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
public class Exports {

	@Id
	private String exportNo;
	
	@NotNull
	@ManyToOne
	private Seller seller;
	
	@NotNull
	private String salesChannel;
	
	@NotNull
	private String ordererName;

	@NotNull
	private String address;
	
	@NotNull
	private Timestamp orderDate;
	
	/**
	 * Exports -> ExportsDTO
	 * @param exportProductRepository
	 * @return
	 */
	public ExportsDTO toExportsDTO(ExportProductRepository exportProductRepository) {
		return ExportsDTO
				.builder()
				.address(address)
				.exportNo(exportNo)
				.orderDate(orderDate)
				.ordererName(ordererName)
				.orderStatus(setOrderStatus(exportNo, exportProductRepository))
				.salesChannel(salesChannel)
				.sellerNo(seller.getSellerNo())
				.build();
	}
	
	/**
	 * 출고 목록에서 주문 상태 얻기 위한 메서드
	 * 주문이 취소된 경우: 주문취소
	 * 모든 물건이 출고됨: 출고완료
	 * 나머지: 준비중
	 * 
	 * @param exportNo
	 * @return
	 */
	private String setOrderStatus(String exportNo, ExportProductRepository exportProductRepository) {
		String status = "출고완료";
		
		Long cancelCnt = exportProductRepository.countByExports_ExportNoAndOrderStatus(exportNo, "주문취소");
		Long waitingCnt = exportProductRepository.countByExports_ExportNoAndOrderStatus(exportNo, "출고대기");
		
		if(cancelCnt > 0) status = "주문취소";
		else if(waitingCnt > 0) status = "준비중";
		
		return status;		
	}
}
