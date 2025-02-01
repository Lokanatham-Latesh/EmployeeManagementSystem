package com.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	/**
	 * 
	 * 
	 */
	@Bean
	OpenAPI myCustomConfig() {
		return new OpenAPI().info(new Info().title("Employee Management System API")
				.description("API Documentation for managing employees and departments").version("1.0.0")
				.termsOfService("http://swagger.io/terms/")
				.contact(new io.swagger.v3.oas.models.info.Contact().name("Support")
						.url("http://www.mindfire.com/support").email("lokanathamlatesh@gmail.com"))
				.license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0")
						.url("http://www.apache.org/licenses/LICENSE-2.0")));

	}
}
