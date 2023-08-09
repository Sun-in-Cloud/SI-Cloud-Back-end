package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.LoginRequestDTO;
import com.shinhan.sunInCloud.dto.LoginResponseDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/3pl/auth/register")
	public boolean register3PL(@RequestBody ThreePLDTO threePLDTO) {
		return authService.register3PL(threePLDTO);
	}
	
	@PostMapping("/seller/auth/register")
	public boolean registerSeller(@RequestBody SellerDTO sellerDTO) {
		return authService.registerSeller(sellerDTO);
	}
	
	@PostMapping("/auth/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return authService.login(loginRequestDTO);
	}
	
}
