package com.shinhan.sunInCloud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.Warehouse;
import com.shinhan.sunInCloud.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseService {
	private final WarehouseRepository warehouseRepository;
	
	public List<Warehouse> saveAll(List<Warehouse> warehouses) {
		return warehouseRepository.saveAll(warehouses);
	}
}
