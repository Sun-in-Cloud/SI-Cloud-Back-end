package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.MatchingConditionDTO;
import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.MatchingSellerListDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
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
	 * 화주사 번호로 매칭 정보 찾는 메서드
	 * @param sellerNo
	 * @return
	 */
	public Matching findBySellerNo(Long sellerNo) {
		return matchingRepository.findBySeller_SellerNo(sellerNo);
	}
	
	/**
	 * 계약 맺는 메서드
	 * @param matchingDTO
	 * @return
	 */
	public boolean contract(MatchingDTO matchingDTO) {
		Long sellerNo = matchingDTO.getSellerNo();
		Seller seller = sellerService.findById(sellerNo);
		Warehouse warehouse = warehouseService.findByLocation(matchingDTO.getLocation(), matchingDTO.getThreePLNo());
		Matching tmpMatching = matchingRepository.findBySeller_SellerNo(sellerNo);
		
		// 창고가 없는 경우, 남은 자리가 없는 경우 실패
		if(warehouse == null || (warehouse.getThreePL().getCntContracted() == warehouse.getThreePL().getCntTotal())) return false;
		// 다른 사람과 계약된 경우 실패
		if(tmpMatching == null) return false;
		// 나와 이미 계약된 경우 실패
		if(tmpMatching.getSeller() == seller) return false;
		
		Matching matching = Matching
				.builder()
				.endDate(TimestampUtil.convertStringToDate(matchingDTO.getEndDate()))
				.seller(seller)
				.warehouse(warehouse)
				.build();
		
		Matching savedMatching = matchingRepository.save(matching);
		
		return savedMatching != null;
	}
	
	/**
	 * 검색 조건에 맞는 화주사 검색
	 * @param matchingConditionDTO
	 * @return
	 */
	public MatchingSellerListDTO searchingSellerByCondition(MatchingConditionDTO matchingConditionDTO) {
		List<MatchingDTO> matchingSellerDTOs = new ArrayList<>();
		Page<Seller> findedSellers = sellerService.findByMatchingCondition(matchingConditionDTO);
		
		for(Seller findedSeller : findedSellers) {
			MatchingDTO matchingSeller = findedSeller.toMatchingSellerDTO(matchingRepository.findBySeller_SellerNo(findedSeller.getSellerNo()));
			matchingSellerDTOs.add(matchingSeller);
		}
		
		MatchingSellerListDTO matchingSellerListDTO = MatchingSellerListDTO
				.builder()
				.totalPage(findedSellers.getTotalPages())
				.matchingCompanies(matchingSellerDTOs)
				.build();
		
		return matchingSellerListDTO;
	}
}
