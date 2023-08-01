package com.shinhan.sunInCloud.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sunInCloud.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, Long>{
	
	public Seller findByBusinessNo(String businessNo);
	
}
