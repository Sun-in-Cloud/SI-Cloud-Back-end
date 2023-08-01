package com.shinhan.sunInCloud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.Seller;
import com.shinhan.sunInCloud.repository.OrderRepository;
import com.shinhan.sunInCloud.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class SellerService {
		
	private final SellerRepository sellerRepo;
	private final OrderRepository orderRepo;
	
	//회원가입시 판매자 등록
	public Seller save(Seller seller){
		return sellerRepo.save(seller);
	}
	
	//사업자 번호 조회
	public Seller findByBusinessNo(String no) {
		return sellerRepo.findByBusinessNo(no);
	}
	
	//1.입고 예정 리스트 등록
	//1.1 발주 조회->목록
	public List<Order> findByOrderNo(String orderNo){
		return (List<Order>) orderRepo.findByOrderNo(orderNo);
	}
	
	//1.2 발주 조회->상세
	public Order findByOrderProductNo(Long orderProductNo) {
		return orderRepo.findByOrderProductNo(orderProductNo);
	}
	
	//1.3 발주 조회->등록
	//public Product saveProduct(Product ) {
		
	//}
	
	
}

