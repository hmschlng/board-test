package com.ktds.board.common.auth.util;

import com.ktds.board.auth.api.service.impl.RedisService;
import com.ktds.board.common.auth.enumtype.JwtTokenStatusCode;
import com.ktds.board.common.auth.enumtype.TokenType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import static com.ktds.board.common.auth.enumtype.TokenType.ACCESS_TOKEN;
import static com.ktds.board.common.auth.enumtype.TokenType.REFRESH_TOKEN;

@Slf4j
@Service
public class JwtTokenProvider {

	private final JwtUtil jwtUtil;
	private final Long accessTokenLifetime;
	private final Long refreshTokenLifetime;
	private final RedisService redisService;

	@Autowired
	public JwtTokenProvider(
		JwtUtil jwtUtil,
		@Value("${jwt.access-token-expiration-period}") Long accessTokenLifetime,
		@Value("${jwt.refresh-token-expiration-period}") Long refreshTokenLifeTime,
		RedisService redisService
	) {
		this.jwtUtil = jwtUtil;
		this.accessTokenLifetime = accessTokenLifetime;
		this.refreshTokenLifetime = refreshTokenLifeTime;
		this.redisService = redisService;
	}

	public String resolveToken(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer-")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public Token generateToken(Long userId, String role) {
		var body = Map.of(
			"userId", userId,
			"role", role
		);

		String accessToken = generateAccess(body);
		String refreshToken = generateRefresh(body);

		return new Token(accessToken, refreshToken);
	}

	public String generateAccess(Map<String, ? extends Serializable> body) {
		return jwtUtil.createToken(body, this.getExpiration(ACCESS_TOKEN));
	}

	public String generateRefresh(Map<String, ? extends Serializable> body) {
		return jwtUtil.createToken(body, this.getExpiration(REFRESH_TOKEN));
	}

	public JwtTokenStatusCode validateToken(String token) {
		return jwtUtil.validateToken(token);
	}

	private long getExpiration(TokenType tokenType) {
		return switch (tokenType) {
			case ACCESS_TOKEN -> this.accessTokenLifetime;
			case REFRESH_TOKEN -> this.refreshTokenLifetime;
		};
	}

	 public String getSavedRefresh(String key) {
	 	 return redisService.getData(key);
	 }

	 public void cacheRefreshToken(String key, String value) {
	 	redisService.setDataWithExpiration(key, value, refreshTokenLifetime);
	 }

	public Long getUserId(String token) {
		return jwtUtil.parseClaims(token)
			.get("userId", Long.class);
	}

	 public Claims getClaims(String token) {
	 	return jwtUtil.parseClaims(token);
	 }

	public Long getAccessTokenLifetime() {
		return accessTokenLifetime;
	}

	public Long getRefreshTokenLifetime() {
		return refreshTokenLifetime;
	}

}