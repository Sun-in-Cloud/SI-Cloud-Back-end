package com.shinhan.sunInCloud.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sunInCloud.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	
	Order findByOrderNo(String orderNo);
	
	Order findByOrderProductNo(Long orderProductNo);
}
