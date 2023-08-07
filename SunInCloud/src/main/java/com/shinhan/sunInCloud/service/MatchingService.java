package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.MatchingSellerDTO;
import com.shinhan.sunInCloud.entity.Matching;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.Warehouse;
import com.shinhan.sunInCloud.repository.MatchingRepository;
import com.shinhan.sunInCloud.util.TimestampUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchingService {
	private final MatchingRepository matchingRepository;
	private final SellerService sellerService;
	private final WarehouseService warehouseService;
	
	/**
	 * 3pl 번호로 매칭 정보 찾는 메서드
	 * @param threePLNo
	 * @return
	 */
	public List<Matching> findByThreePLNo(Long threePLNo) {
		return matchingRepository.findByWarehouse_ThreePL_ThreePLNo(threePLNo);
	}
	
	/**
	 * 계약 맺는 메서드
	 * @param matchingDTO
	 * @return
	 */
	public boolean contract(MatchingDTO matchingDTO) {
		Seller seller = sellerService.findById(matchingDTO.getSellerNo());
		Warehouse warehouse = warehouseService.findByLocation(matchingDTO.getLocation(), matchingDTO.getThreePLNo());
		
		if(warehouse == null) return false;
		
		Matching matching = Matching
				.builder()
				.endDate(TimestampUtil.convertStringToDate(matchingDTO.getEndDate()))
				.seller(seller)
				.warehouse(warehouse)
				.build();
		
		Matching savedMatching = matchingRepository.save(matching);
		
		return savedMatching != null;
	}
	
	public List<MatchingSellerDTO> searcingByCondition(MatchingSellerDTO matchingSellerDTO) {
		List<MatchingSellerDTO> matchingSellerDTOs = new ArrayList<>();
		List<Seller> findedSellers = sellerService.findByMatchingCondition(matchingSellerDTO);
		System.out.println("findedSellers: " + findedSellers);
		
		for(Seller findedSeller : findedSellers) {
			matchingSellerDTOs.add(findedSeller.toMatchingSellerDTO(matchingSellerDTO));
		}
		
		return matchingSellerDTOs;
	}
}
