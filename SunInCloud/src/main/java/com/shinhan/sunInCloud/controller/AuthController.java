package com.shinhan.sunInCloud.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sunInCloud.dto.LoginRequestDTO;
import com.shinhan.sunInCloud.dto.LoginResponseDTO;
import com.shinhan.sunInCloud.dto.SellerDTO;
import com.shinhan.sunInCloud.dto.ThreePLDTO;
import com.shinhan.sunInCloud.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API Document")
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/3pl/auth/register")
	@Operation(summary = "register3PL", description = "register the given 3PL")
	public boolean register3PL(@RequestBody ThreePLDTO threePLDTO) {
		return authService.register3PL(threePLDTO);
	}
	
	@PostMapping("/seller/auth/register")
	@Operation(summary = "registerSeller", description = "register the given seller")
	public boolean registerSeller(@RequestBody SellerDTO sellerDTO) {
		return authService.registerSeller(sellerDTO);
	}
	
	@PostMapping("/auth/login")
	@Operation(summary = "login", description = "login with the given id and password")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return authService.login(loginRequestDTO);
	}
	
}
