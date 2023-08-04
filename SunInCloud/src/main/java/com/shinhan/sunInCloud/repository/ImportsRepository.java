package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shinhan.sunInCloud.entity.Imports;

public interface ImportsRepository extends JpaRepository<Imports, Long> {
	
	//1.입고 얘정 리스트 상세 조회
	//Imports findByImportProduct_ImportNo(Long sellerNo, Pageable pageable);
	
	//2. 입고 예정, 내역 리스트 목록 sellerNo로 찾기
	Page<Imports> findBySeller_SellerNo(Long sellerNo, Pageable pageables);

	
	//4.리턴값 List이고, Id로 입고 상세 내역 찾기
	//List<Imports> findByImportNo(Long importNo);
}
