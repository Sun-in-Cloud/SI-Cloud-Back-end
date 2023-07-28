package com.shinhan.sunInCloud.service;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SellerService {
		
	private final SellerRepository sellerRepo;
	
	//회원가입시 판매자 등록
	public Seller save(Seller seller){
		return sellerRepo.save(seller);
	}
	
	public Seller findByBusinessNo(String no) {
		return sellerRepo.findByBusinessNo(no);
	}
}

