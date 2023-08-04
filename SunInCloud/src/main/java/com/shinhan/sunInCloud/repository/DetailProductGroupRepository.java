package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.DetailProductGroup;

public interface DetailProductGroupRepository extends JpaRepository<DetailProductGroup, Long> {
	public DetailProductGroup findByGroupName(String groupName);
	public List<DetailProductGroup> findByProductGroup_GroupName(String groupName);
}
