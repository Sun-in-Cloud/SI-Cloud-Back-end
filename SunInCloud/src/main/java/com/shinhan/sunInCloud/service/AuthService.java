package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.Warehouse;
import com.shinhan.sunInCloud.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final ThreePLService threePLService;
	private final WarehouseService warehouseService;
	private final ProductGroupService productGroupService;
	private final SellerService sellerService;
	private final UserRepository userRepository;
	
	
	/**
	 * 3PL 회원가입하는 메서드
	 * 
	 * @param threePLDTO
	 * @return
	 */
	@Transactional
	public boolean register3PL(ThreePLDTO threePLDTO) {
		// 중복되는 아이디
		if(userRepository.findByLoginId(threePLDTO.getLoginId()) != null) return false;
		// 중복되는 사업자번호
		if(threePLService.findByBusinessNo(threePLDTO.getBusinessNo()) != null) return false;
		
		List<Warehouse> warehouses = new ArrayList<>();
		
		User user = threePLDTO.toUser();
		ProductGroup productGroup = productGroupService.findByGroupName(threePLDTO.getProductGroupName());
		ThreePL threePL = threePLDTO.toThreePL(productGroup, user);
		ThreePL savedThreePL = threePLService.register(threePL);
		
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
