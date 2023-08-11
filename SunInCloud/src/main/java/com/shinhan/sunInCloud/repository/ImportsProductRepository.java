package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.Imports;

public interface ImportsProductRepository extends JpaRepository<ImportProduct, Long> {

	List<ImportProduct> findByImports_ImportNo(Long importNo);
	//List<ImportProduct> findByImports_ImportNo(Long importNo);
	Long countByImports_ImportNo(Long importNo);
	Imports save(Imports build);
	List<ImportProduct> findAllByImportProductNo(Long importNo);
	List<ImportProduct> findByImports_ImportNoAndImportAmountIsNotNull(Long importNo);
	
}
