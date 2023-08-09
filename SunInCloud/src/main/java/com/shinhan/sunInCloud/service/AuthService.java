package com.shinhan.sunInCloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.dto.LoginRequestDTO;
import com.shinhan.sunInCloud.dto.LoginResponseDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.dto.UserDTO;
import com.shinhan.sunInCloud.entity.Matching;
import com.shinhan.sunInCloud.entity.ProductGroup;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.entity.User;
import com.shinhan.sunInCloud.entity.UserType;
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
	private final MatchingService matchingService;
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
	
	/**
	 * 화주사 회원가입하는 메서드
	 * @param sellerDTO
	 * @return
	 */
	@Transactional
	public boolean registerSeller(SellerDTO sellerDTO) {
		// 중복되는 아이디
		if(userRepository.findByLoginId(sellerDTO.getLoginId()) != null) return false;
		// 중복되는 사업자번호
		if(sellerService.findByBusinessNo(sellerDTO.getBusinessNo()) != null) return false;
		
		User user = sellerDTO.toUser();
		ProductGroup productGroup = productGroupService.findByGroupName(sellerDTO.getProductGroupName());
		Seller seller = sellerDTO.toSeller(productGroup, user);
		
		Seller savedSeller = sellerService.save(seller);
		
		return savedSeller != null;
	}
	
	/**
	 * WMS, 3PL, 화주사 로그인
	 * @param loginRequestDTO
	 * @return
	 */
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByLoginIdAndLoginPassword(loginRequestDTO.getLoginId(), loginRequestDTO.getLoginPassword());
		
		if(user == null) return null;

		LoginResponseDTO loginResponseDTO = LoginResponseDTO
				.builder()
				.userNo(user.getUserNo())
				.userType(user.getUserType().toString())
				.build();
		
		if(user.getUserType().equals(UserType.THREE_PL)) {
			List<UserDTO> userDTOs = new ArrayList<>();
			List<Matching> matchings = matchingService.findByThreePLNo(user.getUserNo());
			
			for(Matching matching : matchings) {
				UserDTO userDTO = UserDTO
						.builder()
						.sellerNo(matching.getSeller().getSellerNo())
						.companyName(matching.getSeller().getCompanyName())
						.build();
				userDTOs.add(userDTO);
			}
			
			loginResponseDTO.setSellers(userDTOs);
		}
		
		return loginResponseDTO;
	}
}
