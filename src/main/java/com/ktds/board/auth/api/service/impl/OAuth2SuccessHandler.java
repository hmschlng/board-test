package com.ktds.board.auth.api.service.impl;

import com.github.f4b6a3.tsid.Tsid;
import com.ktds.board.auth.api.dto.UserInfoDto;
import com.ktds.board.auth.db.entity.UserAuth;
import com.ktds.board.auth.db.entity.enumtype.Role;
import com.ktds.board.auth.db.repository.UserAuthRepository;
import com.ktds.board.common.auth.enumtype.TokenType;
import com.ktds.board.common.auth.util.JwtTokenProvider;
import com.ktds.board.user.db.entity.UserInfo;
import com.ktds.board.user.db.repository.UserInfoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserAuthRepository userAuthRepository;
    private final UserInfoRepository userInfoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private String REDIRECT_URI = "http://localhost:5000/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        var attributes = ((OAuth2User)authentication.getPrincipal()).getAttributes();

        var socialId = Optional.of(String.valueOf(attributes.get("id")))
                .orElseThrow(() -> new OAuth2AuthenticationException("인증서버에서 받은 ID 정보가 없습니다."));

        var provider = Optional.of(String.valueOf(attributes.get("provider")))
                .orElseThrow(() -> new OAuth2AuthenticationException("인증 공급자 정보가 없습니다."));

        UserInfoDto userInfoDto;

        // 1. ( OAuthId ) 을 기준으로 회원 정보를 받아온다.
        var userAuth = findByOAuthIdAndProvider(socialId, provider);

        // 최초 로그인인지 판별
        var isInitialLogin = "true";

        // 2. 최초 로그인일때, 회원가입( UUID 생성 )을 진행한다.
        if (Objects.isNull(userAuth)) {
            log.info("============================== 최초 로그인 ==============================" );

            // 2-1. 회원 정보 저장 ( userDto 의 oauthId 에 해당하는 userId 생성  )
            userAuth = saveByProviderType(provider, attributes);

        }
        else {
            log.info("================================== 기존 회원 로그인 ===================================");
            isInitialLogin = "false";
        }

        var token = jwtTokenProvider.generateToken(userAuth.getId(), Role.USER.getKey());

        log.info("JWT 토큰 = {}", token);

        response.setHeader("Initial", isInitialLogin);
        response.setHeader(TokenType.ACCESS_TOKEN.getKey(), "Bearer-" + token.getAccessToken());

        // 3-1. 쿠키 생성 및 Refresh Token 저장
        Cookie cookie = new Cookie("refreshToken", token.getRefreshToken());
        cookie.setPath("/"); // Cookie의 유효 경로 설정 (루트 경로로 설정하면 전체 사이트에서 접근 가능)
        cookie.setMaxAge(jwtTokenProvider.getRefreshTokenLifetime().intValue()); // Cookie의 유효 기간 설정 (예: 30일)
        cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
//        cookie.setSecure(true); // HTTPS에서만 전송하도록 설정 (필요한 경우)

        response.addCookie(cookie);

        // 3-2. 헤더에 access token 저장
        response.setHeader(TokenType.ACCESS_TOKEN.getKey(), "Bearer-" + token.getAccessToken());

        // 3-3. Front Page로 리다이렉트 수행
        var targetUrl = UriComponentsBuilder.fromUriString(REDIRECT_URI).build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    private UserAuth findByOAuthIdAndProvider(String oAuthId, String provider) {
        return switch(provider) {
            case "kakao" -> userAuthRepository.findByKakaoOAuthId(oAuthId).orElse(null);
            case "naver" -> userAuthRepository.findByNaverOAuthId(oAuthId).orElse(null);
            case "google" -> userAuthRepository.findByGoogleOAuthId(oAuthId).orElse(null);
            default -> throw new IllegalArgumentException("등록되지 않은 공급자명입니다. -> " + provider);
        };
    }

    private UserAuth saveByProviderType(String provider, Map<String, Object> attributes) {
        var oAuthId = String.valueOf(attributes.get("id"));

        // // UserAuth와 UserInfo에 공통으로 쓰일 ID 생성
        // var generatedID = Tsid.fast().toLong();

        var userAuth = UserAuth.builder()
                .email(String.valueOf(attributes.get("email")))
                .role(Role.valueOf(String.valueOf(attributes.get("role"))))
                .build();

        // 인증 공급자에 따라 다른 칼럼에 OAuth ID 저장
        userAuth = switch(provider) {
            case "kakao" -> userAuth.toBuilder().kakaoOAuthId(oAuthId).build();
            case "naver" -> userAuth.toBuilder().naverOAuthId(oAuthId).build();
            case "google" -> userAuth.toBuilder().googleOAuthId(oAuthId).build();
            default -> throw new IllegalStateException("등록되지 않은 공급자명입니다. -> " + provider);
        };

        // 사용자 인증 정보 저장
        userAuthRepository.save(userAuth);

        // 사용자 프로필 정보 저장
        userInfoRepository.save(UserInfo.builder()
                .id(userAuth.getId())
                .email(userAuth.getEmail())
                .nickname(String.valueOf(attributes.get("nickname")))
                .profileImg(String.valueOf(attributes.get("image")))
                .build());

        return userAuth;
    }
}
