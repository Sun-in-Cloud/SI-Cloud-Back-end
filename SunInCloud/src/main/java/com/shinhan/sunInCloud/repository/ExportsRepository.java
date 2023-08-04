package com.shinhan.sunInCloud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Exports;

public interface ExportsRepository extends JpaRepository<Exports, Long>{
	Page<Exports> findAllBySeller_SellerNoOrderByOrderDateDesc(Long sellerNo, Pageable pageable);
}
