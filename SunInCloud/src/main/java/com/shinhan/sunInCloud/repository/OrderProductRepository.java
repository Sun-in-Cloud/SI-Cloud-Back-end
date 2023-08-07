package com.shinhan.sunInCloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.sunInCloud.entity.Order;
import com.shinhan.sunInCloud.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{

	List<OrderProduct> findAllByOrder_OrderNo(Long orderNo);
    List<OrderProduct> findByOrder_OrderNo(Long orderNo);//
}
