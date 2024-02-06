package com.ktds.board.common.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.ktds.board.common.auth.enumtype.JwtTokenStatusCode.ACCESSED;
import static com.ktds.board.common.auth.enumtype.JwtTokenStatusCode.EXPIRED;
import static com.ktds.board.common.auth.enumtype.JwtTokenStatusCode.DENIED;

import java.io.Serializable;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import com.ktds.board.common.auth.enumtype.JwtTokenStatusCode;

@Slf4j
@Component
public class JwtUtil {

	private final Key key;

	public JwtUtil(
		@Value("${jwt.secret}") String secret
	) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
	}

	// Jwt 생성
	public String createToken(Map<String, ? extends Serializable> body, long expireTime) {
		var claims = Jwts.claims();

		claims.putAll(body);
		log.debug(" -> claim 생성 완료");

		var now = ZonedDateTime.now();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(Date.from(now.toInstant()))
			.setExpiration(Date.from((now.plusSeconds(expireTime).toInstant())))
			.signWith(this.key, SignatureAlgorithm.HS256)
			.compact();
	}

	// Jwt 검증
	public JwtTokenStatusCode validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
			log.debug("jwt token 인증 성공");
			return ACCESSED;
		} catch (ExpiredJwtException e) {
			log.debug("만료된 토큰");
			return EXPIRED;
		} catch (SecurityException | MalformedJwtException e) {
			log.debug("올바르지 않은 토큰 양식");
		} catch (UnsupportedJwtException e) {
			log.debug("지원되지 않는 토큰");
		} catch (IllegalArgumentException e) {
			log.debug("Jwt 컨텐츠가 비어있음");
		} catch (JwtException e) {
			log.info("JwtException = {}", e.getMessage());
		}
		return DENIED;
	}

	public Claims parseClaims(String token) {
		try {
			var parser = Jwts.parserBuilder()
				.setSigningKey(key)
				.build();

			return parser.parseClaimsJws(token).getBody();

		} catch(ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
