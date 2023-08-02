package com.shinhan.sunInCloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.ExportProduct;

public interface ExportProductRepository extends JpaRepository<ExportProduct, Long>{
	Long countByExports_ExportNoAndOrderStatus(String exportNo, String orderStatus);
}
