package com.shinhan.sunInCloud.service;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.ImportProduct;
import com.shinhan.sunInCloud.entity.ThreePL;
import com.shinhan.sunInCloud.repository.SellerRepository;
import com.shinhan.sunInCloud.repository.ThreePLRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreePLService {

	private final ThreePLRepository threePLRepository;
	private final SellerRepository sellerRepo;
	/**
	 * 3PL 등록 메소드
	 * @param threePL
	 * @return 등록된 threePL
	 */
	public ThreePL register(ThreePL threePL) {
		return threePLRepository.save(threePL);
	}
	
	
	//입고등록에서 입고 등록 조회
	public ImportProduct selectImportProduct(List<Product> product) {
		return threePLRepository.findBy
	}
}
