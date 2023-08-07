package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shinhan.sunInCloud.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
	@Query(value = "select * from warehouse "
			+ "where threepl_no = :threePLNo and warehouse_no not in "
			+ "(select warehouse_no from matching)", nativeQuery = true)
	public List<Warehouse> findByWarehouseNotInMatching(@Param("threePLNo")Long threePLNo);
	public Warehouse findByLocationAndThreePL_ThreePLNo(String location, Long threePLNo);
}
