package com.shinhan.sunInCloud.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.entity.Matching;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.repository.ThreePLRepository;
import com.shinhan.sunInCloud.util.TimestampUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreePLService {

	private final ThreePLRepository threePLRepository;
	private final SellerService sellerService;
	private final MatchingService matchingService;
	private final ExportsService exportsService;
	/**
	 * 3PL 등록 메소드
	 * @param threePL
	 * @return 등록된 threePL
	 */
	public ThreePL register(ThreePL threePL) {
		return threePLRepository.save(threePL);
	}
	
	
	//입고등록에서 입고 등록 조회
//	public ImportProduct selectImportProduct(List<Product> product) {
//		return threePLRepository.findByProduct
//	}
//	

	
	/**
	 * 3PL 정보 상세조회
	 * @param threePLNo
	 * @return
	 */
	public ThreePLDTO threePLDetail(Long threePLNo) {
		ThreePL threepl = threePLRepository.findById(threePLNo).orElse(null);
		List<Matching> matchings = matchingService.findByThreePLNo(threePLNo);
		List<MatchingDTO> matchingDTOs = new ArrayList<>();
		long exportCnt = 0;
		Timestamp endDate = null;
		
		for(Matching matching: matchings) {
			exportCnt += exportsService.getNumberOfSalesYearly(matching.getSeller().getSellerNo(), TimestampUtil.getLastYear() + 1);
			MatchingDTO matchingDTO = matching.getSeller().toMatchingSellerDTO(matching);
			matchingDTOs.add(matchingDTO);
			
			if(endDate == null) {
				endDate = matching.getEndDate();
			} else {
				if(endDate.compareTo(matching.getEndDate()) > 0) {
					endDate = matching.getEndDate();
				}
			}
		}
		
		return threepl.toThreePLDTO(endDate == null ? null : TimestampUtil.convertTimestampToDate(endDate), exportCnt, matchingDTOs);
	}
}
