package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.dto.ImportProductDTO;
import com.shinhan.sunInCloud.dto.ImportsDTO;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;

public interface ImportsRepository extends JpaRepository<ImportProduct, Long> {
	Imports findByImportProduct_ImportNo(Long sellerNo, Pageable pageable);
	List<ImportProductDTO> findByImportNo(Long importNo);	
	List<ImportsDTO> findBySellerNo(Long sellerNo);
}
