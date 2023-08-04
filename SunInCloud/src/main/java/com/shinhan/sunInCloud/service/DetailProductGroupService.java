package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ProductGroupDTO;
import com.shinhan.sunInCloud.entity.DetailProductGroup;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.DetailProductGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailProductGroupService {
	private final DetailProductGroupRepository detailProductGroupRepository;
	private final SellerService sellerService;

	/**
	 * 새로운 DetailProductGroup을 등록하는 메서드
	 * 
	 * @param detailProductGroup
	 * @return
	 */
	public DetailProductGroup register(DetailProductGroup detailProductGroup) {
		return detailProductGroupRepository.save(detailProductGroup);
	}
	
	/**
	 * groupName 기반 세부 상품군 검색
	 * 
	 * @param groupName
	 * @return
	 */
	public DetailProductGroup findByGroupName(String groupName) {
		return detailProductGroupRepository.findByGroupName(groupName);
	}
	
	/**
	 * 화주사의 상품군에 해당하는 세부 상품군 리스트 조회
	 * @param sellerNo
	 * @return
	 */
	public List<ProductGroupDTO> findByProductGroup(Long sellerNo) {
		List<ProductGroupDTO> productGroupDTOs = new ArrayList<>();
		Seller seller = sellerService.findById(sellerNo);
		
		List<DetailProductGroup> detailProductGroups = detailProductGroupRepository
				.findByProductGroup_GroupName(seller.getProductGroup().getGroupName());
		
		for(DetailProductGroup detailProductGroup: detailProductGroups) {
			ProductGroupDTO productGroupDTO = ProductGroupDTO.builder().groupName(detailProductGroup.getGroupName()).build();
			productGroupDTOs.add(productGroupDTO);
		}
		
		return productGroupDTOs;
	}
}
