package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.MatchingConditionDTO;
import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.dto.UserDTO;
import com.shinhan.sunInCloud.dto.UserListDTO;
import com.shinhan.sunInCloud.entity.Matching;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.MatchingRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.repository.SellerRepository;
import com.shinhan.sunInCloud.repository.ThreePLRepository;
import com.shinhan.sunInCloud.util.TimestampUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SellerService {
		
	private final SellerRepository sellerRepo;
	private final OrderRepository orderRepo;
	private final ImportsRepository importsRepo;
	private final ExportProductRepository exportProductRepository;
	private final MatchingRepository matchingRepository;
	private final ThreePLRepository threePLRepository;
	
	//회원가입시 판매자 등록
	public Seller save(Seller seller){
		return sellerRepo.save(seller);
	}
	
	//사업자 번호 조회
	public Seller findByBusinessNo(String no) {
		return sellerRepo.findByBusinessNo(no);
	}
	
	/**
	 * PK로 조회 (sellerNo)
	 * 
	 * @param sellerNo
	 * @return
	 */
	public Seller findById(Long sellerNo) {
		return sellerRepo.findById(sellerNo).orElse(null);
	}
	
	/**
	 * 매칭 검색 조건에 맞는 화주사 조회
	 * @param matchingSellerDTO
	 * @return
	 */
	public Page<Seller> findByMatchingCondition(MatchingConditionDTO matchingConditionDTO) {
		return sellerRepo.findByMatchingCondition(matchingConditionDTO.getProductGroup(), matchingConditionDTO.getAddress(), 
				matchingConditionDTO.getNumValue(), matchingConditionDTO.getContractPeriod(), 
				PageRequest.of(matchingConditionDTO.getPageNum() - 1, matchingConditionDTO.getCountPerPage()));
	}
	 
	/**
	 * 화주사 정보 상세 조회
	 * @param sellerNo
	 * @return
	 */
	public SellerDTO sellerDetail(Long sellerNo) {
		Seller seller = sellerRepo.findById(sellerNo).orElse(null);
		
		Long sales = exportProductRepository.getYearlySales(sellerNo, TimestampUtil.getLastYear());
		Long exportCnt = exportProductRepository.getSalesCountOfYear(sellerNo, TimestampUtil.getLastYear());
		Matching matching = matchingRepository.findBySeller_SellerNo(sellerNo);
		
		MatchingDTO matchingDTO = null;
		
		if(matching != null) {
			matchingDTO = MatchingDTO
					.builder()
					.companyName(matching.getWarehouse().getThreePL().getCompanyName())
					.endDate(TimestampUtil.convertTimestampToDate(matching.getEndDate()))
					.location(matching.getWarehouse().getLocation())
					.productGroup(matching.getWarehouse().getThreePL().getProductGroup().getGroupName())
					.build();
		}
		
		return seller.toSellerDTO(matchingDTO, sales, exportCnt);
	}
	
	/**
	 * 계약된 3PL 상세 조회
	 * @param threePLNo
	 * @param sellerNo
	 * @return
	 */
	public ThreePLDTO contractedThreePLDetail(Long sellerNo) {
		Matching matching = matchingRepository.findBySeller_SellerNo(sellerNo);
		
		if(matching == null) return null;
		
		ThreePL threePL = threePLRepository.findById(matching.getWarehouse().getThreePL().getThreePLNo()).orElse(null);
		
		return threePL.toThreePLDTO(TimestampUtil.convertTimestampToDate(matching.getEndDate()), 0, null);
	}
	
	/**
	 * 전체 화주사 목록 조회
	 * @param pageNum
	 * @param countPerPage
	 * @return
	 */
	public UserListDTO findAllSeller(int pageNum, int countPerPage) {
		Page<Seller> sellers = sellerRepo.findAllByOrderByCompanyName(PageRequest.of(pageNum - 1, countPerPage));
		List<UserDTO> userDTOs = new ArrayList<>();
		for(Seller seller : sellers) {
			userDTOs.add(seller.toUserDTO());
		}
		
		UserListDTO userListDTO = UserListDTO
				.builder()
				.totalPage(sellers.getTotalPages())
				.companies(userDTOs)
				.build();
		
		return userListDTO;
	}
}

