package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.Warehouse;
import com.shinhan.sunInCloud.repository.ThreePLRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final ThreePLRepository threePLRepository;
	private final WarehouseService warehouseService;
	private final ProductGroupService productGroupService;
	
	@Transactional
	public boolean register3PL(ThreePLDTO threePLDTO) {
		List<Warehouse> warehouses = new ArrayList<>();
		
		User user = threePLDTO.toUser();
		ProductGroup productGroup = productGroupService.findByGroupName(threePLDTO.getProductGroupName());
		ThreePL threePL = threePLDTO.toThreePL(productGroup, user);
		ThreePL savedThreePL = threePLRepository.save(threePL);
		
		int cnt = savedThreePL.getCntTotal();
		
		for(int i = 0; i < cnt; i++) {
			Warehouse warehouse = Warehouse
					.builder()
					.threePL(savedThreePL)
					.location(String.valueOf((char) ('A' + i)))
					.build();
			warehouses.add(warehouse);
		}
		
		List<Warehouse>savedWarehouses = warehouseService.saveAll(warehouses);
		
		return savedWarehouses != null;
	}
}
