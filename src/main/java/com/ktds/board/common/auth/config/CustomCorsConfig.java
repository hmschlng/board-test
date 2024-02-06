package com.ktds.board.common.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CustomCorsConfig {

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOriginPattern("*");
		corsConfig.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
		corsConfig.setAllowCredentials(false);

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return source;
	}

}
