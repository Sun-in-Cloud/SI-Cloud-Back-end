package com.shinhan.sunInCloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI restAPI() {
		Info info = new Info()
				.title("Sun In Cloud Spring Boot REST API")
				.version("1.0.0")
				.description("REST API docs for Sun In Cloud Spring Boot Server");
		
		return new OpenAPI().components(new Components()).info(info);
	}
}
