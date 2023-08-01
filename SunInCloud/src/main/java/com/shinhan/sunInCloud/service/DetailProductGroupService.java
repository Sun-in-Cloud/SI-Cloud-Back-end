package com.shinhan.sunInCloud.service;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.DetailProductGroup;
import com.shinhan.sunInCloud.repository.DetailProductGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailProductGroupService {
	private final DetailProductGroupRepository detailProductGroupRepository;

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
}
