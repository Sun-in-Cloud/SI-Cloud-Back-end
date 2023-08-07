package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.Warehouse;
import com.shinhan.sunInCloud.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseService {
	private final WarehouseRepository warehouseRepository;
	
	/**
	 * 모든 창고 공간 저장하는 메서드
	 * @param warehouses
	 * @return
	 */
	public List<Warehouse> saveAll(List<Warehouse> warehouses) {
		return warehouseRepository.saveAll(warehouses);
	}
	
	/**
	 * 남은 창고 자리 찾는 메서드
	 * @param threePLNo
	 * @return
	 */
	public List<String> findLeftLocation(Long threePLNo) {
		List<String> locations = new ArrayList<>();
		List<Warehouse> warehouses = warehouseRepository.findByWarehouseNotInMatching(threePLNo);
		
		for(Warehouse warehouse : warehouses) {
			locations.add(warehouse.getLocation());
		}
		
		return locations;
	}
	
	/**
	 * 3pl에서 해당 위치의 창고 찾기
	 * @param location
	 * @param threePLNo
	 * @return
	 */
	public Warehouse findByLocation(String location, Long threePLNo) {
		return warehouseRepository.findByLocationAndThreePL_ThreePLNo(location, threePLNo);
	}
}
