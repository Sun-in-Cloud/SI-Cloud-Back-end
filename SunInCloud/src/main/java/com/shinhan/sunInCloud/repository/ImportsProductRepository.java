package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shinhan.sunInCloud.entity.ImportProduct;

public interface ImportsProductRepository extends JpaRepository<ImportProduct, Long> {
	
}
