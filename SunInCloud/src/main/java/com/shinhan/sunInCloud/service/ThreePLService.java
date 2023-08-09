package com.shinhan.sunInCloud.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.MatchingConditionDTO;
import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.dto.UserDTO;
import com.shinhan.sunInCloud.dto.UserListDTO;
import com.shinhan.sunInCloud.entity.Matching;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.repository.MatchingRepository;
import com.shinhan.sunInCloud.repository.ThreePLRepository;
import com.shinhan.sunInCloud.util.TimestampUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreePLService {

	private final ThreePLRepository threePLRepository;
	private final MatchingRepository matchingRepository;
	private final SellerService sellerService;
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
		List<Matching> matchings = matchingRepository.findByWarehouse_ThreePL_ThreePLNo(threePLNo);
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
	
	/**
	 * 매칭 검색 조건에 맞는 3PL 조회
	 * @param matchingConditionDTO
	 * @return
	 */
	public Page<ThreePL> findByMatchingCondition(MatchingConditionDTO matchingConditionDTO) {
		return threePLRepository.findByMatchingCondition(matchingConditionDTO.getProductGroup(), matchingConditionDTO.getAddress(), 
				matchingConditionDTO.getNumValue(), matchingConditionDTO.getContractPeriod(), 
				PageRequest.of(matchingConditionDTO.getPageNum() - 1, matchingConditionDTO.getCountPerPage()));
	}
	
	/**
	 * 계약된 화주사 목록 조회
	 * @param threePLNo
	 * @return
	 */
	public List<MatchingDTO> findByContractedSeller(Long threePLNo) {
		List<Matching> matchings = matchingRepository.findByWarehouse_ThreePL_ThreePLNo(threePLNo);
		List<MatchingDTO> matchingDTOs = new ArrayList<>();
		
		for(Matching matching : matchings) {
			matchingDTOs.add(matching.toMatchingDTO());
		}
		
		return matchingDTOs;
	}
	
	/**
	 * 모든 3PL 목록 조회
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public UserListDTO findAllThreePL(int pageNum, int countPerPage) {
		Page<ThreePL> threePLs = threePLRepository.findAllByOrderByCompanyName(PageRequest.of(pageNum - 1, countPerPage));
		List<UserDTO> userDTOs = new ArrayList<>();
		for(ThreePL threePL : threePLs) {
			userDTOs.add(threePL.toUserDTO());
		}
		
		UserListDTO userListDTO = UserListDTO
				.builder()
				.totalPage(threePLs.getTotalPages())
				.companies(userDTOs)
				.build();
		
		return userListDTO;
	}
	
	/**
	 * 3PL 사업자번호를 이용해 조회
	 * @param businessNo
	 * @return
	 */
	public ThreePL findByBusinessNo(String businessNo) {
		return threePLRepository.findByBusinessNo(businessNo);
	}
}
