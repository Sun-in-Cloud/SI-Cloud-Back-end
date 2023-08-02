package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	
}
