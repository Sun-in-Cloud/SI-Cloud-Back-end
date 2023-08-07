package com.shinhan.sunInCloud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.MatchingConditionDTO;
import com.shinhan.sunInCloud.dto.MatchingDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.entity.Matching;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ExportProductRepository;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.MatchingRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.repository.SellerRepository;
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
		
		Long sales = exportProductRepository.getSalesCountOfYear(sellerNo, Integer.parseInt(TimestampUtil.getPattern("yyyy")) - 1);
		Long exportCnt = exportProductRepository.getSalesCountOfYear(sellerNo, Integer.parseInt(TimestampUtil.getPattern("yyyy")) - 1);
		Matching matching = matchingRepository.findBySeller_SellerNo(sellerNo);
		
		MatchingDTO matchingDTO = new MatchingDTO();
		
		if(matching != null) {
			matchingDTO.setCompanyName(matching.getWarehouse().getThreePL().getCompanyName());
			matchingDTO.setEndDate(TimestampUtil.convertTimestampToDate(matching.getEndDate()));
			matchingDTO.setLocation(matching.getWarehouse().getLocation());
			matchingDTO.setProductGroup(matching.getWarehouse().getThreePL().getProductGroup().getGroupName());
		}
		
		return seller.toSellerDTO(matchingDTO, sales, exportCnt);
	}
}

