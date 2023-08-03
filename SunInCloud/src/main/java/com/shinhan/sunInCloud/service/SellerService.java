package com.shinhan.sunInCloud.service;

import org.springframework.stereotype.Service;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.ImportsRepository;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SellerService {
		
	private final SellerRepository sellerRepo;
	private final OrderRepository orderRepo;
	private final ImportsRepository importsRepo;
	//회원가입시 판매자 등록
	public Seller save(Seller seller){
		return sellerRepo.save(seller);
	}
	
	//사업자 번호 조회
	public Seller findByBusinessNo(String no) {
		return sellerRepo.findByBusinessNo(no);
	}
	
	/**
	 * PK로 조회 (sellerNo)
	 * 
	 * @param sellerNo
	 * @return
	 */
	public Seller findById(Long sellerNo) {
		return sellerRepo.findById(sellerNo).orElse(null);
	}
	
	
}

