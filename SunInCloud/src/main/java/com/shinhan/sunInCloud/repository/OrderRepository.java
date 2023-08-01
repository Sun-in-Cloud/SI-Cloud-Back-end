package com.shinhan.sunInCloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	//안만들어도 됨 -> id로 바꾸기
//	Order findByOrderNo(Long orderNo);
//	
//	Order findByOrderProductNo(Long orderProductNo);
}
