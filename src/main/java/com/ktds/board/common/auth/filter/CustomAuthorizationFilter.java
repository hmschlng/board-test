package com.ktds.board.common.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktds.board.common.auth.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("doFilterInternal");
		log.info("ㄴ> request uri = " + request.getRequestURI());

		var isExcluded = request.getServletPath().contains("/")
			|| request.getServletPath().contains("swagger")
			|| request.getServletPath().contains("api-docs");

		if (!isExcluded) {   // 인증이 필요할 경우

			String token = request.getHeader("Authorization").substring(7);   // 헤더의 토큰 파싱 (Bearer 제거)

			log.debug("Header - (key:Authorization, value:{}", token);

			try {
				Long userId = jwtTokenProvider.getUserId(token);
				log.debug("획득한 userId = " + userId);

				addAuthorizationHeaders(request, userId);

				SecurityContextHolder.getContext()
					.setAuthentication(getAuthentication(userId));

			} catch (Exception e) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				Map<String, Object> body = new LinkedHashMap<>();
				body.put("code", HttpStatus.UNAUTHORIZED.value());
				body.put("error", e.getMessage());

				new ObjectMapper().writeValue(response.getOutputStream(), body);
			}

			filterChain.doFilter(request, response);
		}

	}

	// 성공적으로 검증이 되었기 때문에 인증된 헤더로 요청을 변경해준다. 서비스는 해당 헤더에서 userId(토큰)를 가져와 사용한다.
	private void addAuthorizationHeaders(HttpServletRequest request, Long userId) {
		request.setAttribute("userId", userId);
	}

	public Authentication getAuthentication(Long userId) {
		return new UsernamePasswordAuthenticationToken(userId, "",
			List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}
}