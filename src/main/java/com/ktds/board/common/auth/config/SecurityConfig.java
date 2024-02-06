package com.ktds.board.common.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.ktds.board.common.auth.filter.CustomAuthorizationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	@Autowired
	private CustomAuthorizationFilter customAuthorizationFilter;

	@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers(
				"/favicon.ico",
				"/error",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/v3/api-docs",
				"/webjars/**"
			)
			.and()
			.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations());    // 정적인 리소스들에 대해서 시큐리티 적용 무시.
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource))
			.authorizeHttpRequests(req -> req
				.requestMatchers("api/article/").permitAll()
				.requestMatchers("/api/category/**").permitAll()
				.requestMatchers("api/auth/**").permitAll()
				.anyRequest().authenticated())
			.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
			.addFilterBefore(customAuthorizationFilter, CustomAuthorizationFilter.class)
			.build();
	}

}