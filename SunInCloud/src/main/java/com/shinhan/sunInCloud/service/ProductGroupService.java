package com.shinhan.sunInCloud.service;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.repository.ProductGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductGroupService {

	private final ProductGroupRepository productGroupRepository;
	
	/**
	 * 새로운 ProductGroup을 등록하는 메소드
	 * @param productGroup
	 * @return
	 */
	public ProductGroup register(ProductGroup productGroup) {
		return productGroupRepository.save(productGroup);
	}
	
	/**
	 * groupName 기반 상품군 검색
	 * @param groupName
	 * @return productGroup or null
	 */
	public ProductGroup findByGroupName(String groupName) {
		return productGroupRepository.findByGroupName(groupName);
	}
}
