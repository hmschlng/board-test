package com.ktds.board.common.auth.config;

import com.ktds.board.auth.api.service.impl.CustomOAuth2UserService;
import com.ktds.board.auth.api.service.impl.CustomUserDetailsService;
import com.ktds.board.auth.api.service.impl.OAuth2SuccessHandler;
import com.ktds.board.common.auth.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	private final CorsConfigurationSource corsConfigurationSource;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final CustomUserDetailsService customUserDetailsService;
	private final String[] allowedUrlPatternList = {
		"/favicon.ico",
		"/error",
		"/api/v2/**",
		"/swagger-resources/**",
		"/swagger-ui.html",
		"/swagger/**",
		"/v2/api-docs",
		"/v3/api-docs",
		"/webjars/**"
	};

	//    @Bean
	//    public SecurityFilterChain resources(HttpSecurity http) throws Exception {
	//        return http
	//            .authorizeHttpRequests(req -> req
	//                    .requestMatchers(allowedUrlPatternList).permitAll()
	//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	//            ).build();
	//    }

	//    @Bean
	//    public WebSecurityCustomizer configure() {
	//        return (web) -> web.ignoring()
	//                .requestMatchers(
	//                        "/favicon.ico",
	//                        "/error",
	//                        "/swagger-resources/**",
	//                        "/swagger-ui/**",
	//                        "/v3/api-docs",
	//                        "/webjars/**"
	//                )
	//                .and()
	//                .ignoring()
	//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());    // 정적인 리소스들에 대해서 시큐리티 적용 무시.
	//    }

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http
		        .authorizeHttpRequests(req -> req
		                .requestMatchers("/api/article/").permitAll()
		                .requestMatchers("/api/category/**").permitAll()
		                .requestMatchers("/api/auth/**").permitAll()
		                .requestMatchers("/login/oauth2/code/*").permitAll()
		                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		                .requestMatchers(allowedUrlPatternList).permitAll()
		                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		                .anyRequest().authenticated())
		        .cors(cors -> cors
		                .configurationSource(corsConfigurationSource))
		        .httpBasic(HttpBasicConfigurer::disable)
		        .csrf(AbstractHttpConfigurer::disable)
		        .formLogin(FormLoginConfigurer::disable)
		        .sessionManagement(manager -> manager
		                .sessionCreationPolicy(STATELESS))
		        .oauth2Login(oAuth2Login -> oAuth2Login
		                .userInfoEndpoint(userInfo -> userInfo
		                        .userService(customOAuth2UserService))
		                .successHandler(oAuth2SuccessHandler))
		        .build();
		// http
		// 	.cors() // cors 설정
		// 	.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
		// 	.and()
		// 	.httpBasic().disable()
		// 	.csrf().disable()
		// 	.formLogin()
		// 	.loginPage("/api/auth/login").permitAll()
		// 	.and()
		// 	.sessionManagement()
		// 	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// 	.and()
		// 	.authorizeHttpRequests()
		// 	.requestMatchers("/api/auth/**").permitAll()
		// 	.requestMatchers("/api/user/swagger").permitAll()
		// 	.requestMatchers("/api/main/swagger").permitAll()
		// 	.requestMatchers(allowedUrlPatternList).permitAll()
		// 	.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		// 	//                .requestMatchers("/").permitAll()
		// 	.and()
		// 	.oauth2Login()
        //     .loginProcessingUrl("/login/oauth2/code/**").permitAll()
		// 	.successHandler(oAuth2SuccessHandler)
		// 	.userInfoEndpoint()
		// 	.userService(customOAuth2UserService);

		// return http.build();

	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
		var provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(bCryptPasswordEncoder());

		return provider;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}