package com.ktds.board.common.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CustomCorsConfig {

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var corsConfig = new CorsConfiguration().applyPermitDefaultValues();
		corsConfig.addAllowedOriginPattern("http://localhost:3012");
		corsConfig.setAllowedMethods(List.of(
				"GET",
				"POST",
				"PUT",
				"PATCH",
				"DELETE",
				"OPTIONS"));
		corsConfig.setAllowedHeaders(List.of(
				"access-control-allow-credentials",
				"access-control-allow-headers",
				"access-control-allow-methods",
				"access-control-allow-origin",
				"authorization",
				"content-type",
				"samesite"));
		corsConfig.setAllowCredentials(true);

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return source;
	}

}
