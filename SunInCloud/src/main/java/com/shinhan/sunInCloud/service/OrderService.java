package com.shinhan.sunInCloud.service;

import org.springframework.stereotype.Service;

import com.shinhan.sunInCloud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
}
