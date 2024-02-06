package com.ktds.board.common.auth.filter;

import com.ktds.board.auth.db.entity.UserAuth;
import com.ktds.board.common.auth.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.ktds.board.auth.db.entity.enumtype.Role.USER;
import static com.ktds.board.common.auth.enumtype.JwtTokenStatusCode.ACCESSED;
import static com.ktds.board.common.auth.enumtype.TokenType.ACCESS_TOKEN;
import static com.ktds.board.common.auth.enumtype.TokenType.REFRESH_TOKEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		var accessToken = jwtTokenProvider.resolveToken(((HttpServletRequest)request).getHeader(ACCESS_TOKEN.getKey()));

		if(Objects.isNull(accessToken)) {
			log.info("no valid JWT token found, uri: {}", ((HttpServletRequest)request).getRequestURI());
		}

		log.info("token = {}", accessToken);

		switch (jwtTokenProvider.validateToken(accessToken)) {
			// 엑세스 토큰이 만료되지 않았을 때,
			case ACCESSED -> {

				var userAuth = UserAuth.builder()
					.id(jwtTokenProvider.getUserId(accessToken))
					.build();

				var authentication = this.getAuthentication(userAuth);

				SecurityContextHolder.getContext().setAuthentication(authentication);

				log.info("set Authentication to security context for '{}', uri = {}", authentication.getName(), ((HttpServletRequest)request).getRequestURI());

			}
			// 엑세스 토큰이 만료되었을 때,
			case EXPIRED -> {

				var userAuth = UserAuth.builder()
					.id(jwtTokenProvider.getUserId(accessToken))
					.build();

				var refreshTokenInHeader = jwtTokenProvider.resolveToken(((HttpServletRequest)request).getHeader(
					REFRESH_TOKEN.getKey()));

				// 캐시에 존재하는 리프레시 토큰 (redis)
				// var refreshTokenInCache = jwtTokenProvider.getSavedRefresh(userAuth.getId());

				if(ACCESSED.equals(jwtTokenProvider.validateToken(refreshTokenInHeader))/* && refreshTokenInHeader.equals(refreshTokenInCache)*/) {
					var newToken = jwtTokenProvider.generateToken(userAuth.getId(), USER.getKey());

					// jwtTokenProvider.cacheRefreshToken(userAuth.getId(), newToken.getRefreshToken());
				}
			}
			default -> log.info("no valid JWT token found, uri: {}", ((HttpServletRequest)request).getRequestURI());
		}

		chain.doFilter(request, response);
	}

	private Authentication getAuthentication(UserAuth auth) {
		return new UsernamePasswordAuthenticationToken(auth, "",
			List.of(new SimpleGrantedAuthority(USER.getKey())));
	}
}
