package com.ktds.board.common.auth.config;

import com.ktds.board.common.auth.filter.CustomAuthorizationFilter;
import com.ktds.board.common.auth.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final JwtAuthFilter jwtAuthFilter;
    private final CorsConfigurationSource corsConfigurationSource;
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
//    @Order(1)
//    public SecurityFilterChain resources(HttpSecurity http) throws Exception {
//        return http
//            .authorizeHttpRequests(req -> req
//                    .requestMatchers(allowedUrlPatternList).permitAll()
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//            ).build();
//    }

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
//    @Order(2)
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("api/article/").permitAll()
                        .requestMatchers("/api/category/**").permitAll()
                        .requestMatchers("api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}