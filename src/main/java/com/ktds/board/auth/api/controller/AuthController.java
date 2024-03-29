package com.ktds.board.auth.api.controller;

import java.util.Arrays;
import java.util.Objects;

import com.ktds.board.auth.api.dto.request.UserLoginReq;
import com.ktds.board.auth.api.dto.request.UserRegisterReq;
import com.ktds.board.auth.api.dto.request.VerifyEmailReq;
import com.ktds.board.auth.api.service.UserAuthService;
import com.ktds.board.common.auth.enumtype.TokenType;
import com.ktds.board.common.auth.util.JwtTokenProvider;
import com.ktds.board.common.response.BaseResponseBody;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.service.UserInfoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "인증 정보", description = "OAuth2 인증 정보 API 문서입니다.")
@RestController
public class AuthController {

    private final UserAuthService userAuthService;
    private final UserInfoService userInfoService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<? extends BaseResponseBody> signup(@RequestBody UserRegisterReq req) {

        log.info("진입!");
        userAuthService.saveOne(req);

        return ResponseEntity
                .ok()
                .body(new BaseResponseBody(HttpStatus.OK, "success"));
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody UserLoginReq req,
        HttpServletResponse res) {
        var userAuth = userAuthService.login(req);

        var token = jwtTokenProvider.generateToken(userAuth.getId(), userAuth.getRole().getKey());
        res.setHeader(TokenType.ACCESS_TOKEN.getKey(), "Bearer-" + token.getAccessToken());

        Cookie cookie = new Cookie(TokenType.REFRESH_TOKEN.getKey(), token.getRefreshToken());
        cookie.setPath("/"); // Cookie의 유효 경로 설정 (루트 경로로 설정하면 전체 사이트에서 접근 가능)
        cookie.setMaxAge(jwtTokenProvider.getRefreshTokenLifetime().intValue()); // Cookie의 유효 기간 설정 (예: 30일)
        cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
        //        cookie.setSecure(true); // HTTPS에서만 전송하도록 설정 (필요한 경우)

        res.addCookie(cookie);

        var userInfo = userInfoService.getOne(UserGetReq.builder()
            .id(userAuth.getId())
            .build());

        userInfo.setToken("Bearer-" + token.getAccessToken());

        return ResponseEntity.ok().body(new BaseResponseBody<>(HttpStatus.OK, userInfo));
    }

    @PostMapping("/verify")
    public ResponseEntity<?extends BaseResponseBody> verifyEmailId(@RequestBody VerifyEmailReq req) throws Exception {
        var code = userAuthService.checkEmailId(req);
        return ResponseEntity
                .ok()
                .body(new BaseResponseBody<>(HttpStatus.OK, code));
    }

    @GetMapping("/signout")
    public ResponseEntity<? extends BaseResponseBody> signout(
        HttpServletResponse res
    ) {
        // 리프레시토큰이 있으면 초기화
        var cookie = new Cookie(TokenType.REFRESH_TOKEN.getKey(), null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
        cookie.setMaxAge(0); // 유효시간을 0으로 설정
        cookie.setHttpOnly(true);
        res.addCookie(cookie); // 응답 헤더에 추가해서 없어지도록 함

        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, "Success"));
    }


//    @GetMapping("/data")
//    @ApiDocumentResponse
//    @Operation(summary = "토큰 조회", description = "Request Header 에 있는 Access Token 값을 반환하는 메서드 입니다.")
//    public ResponseEntity<? extends BaseResponseBody> getTokenData(@RequestHeader("Authorization") String token) {
//
//        return ResponseEntity
//                .ok()
//                .body(new BaseResponseBody<>(200, "Access Token fetched successfully", token));
//    }

//    /**
//     * Refresh Token
//     * FE 에서 Access Token 확인 후, 만료 되었을 때 오는 Re:issue 요청
//     **/
//    @GetMapping(value = "/reissue")
//    @ApiDocumentResponse
//    @Operation(summary = "토큰 조회", description = "Request Header 에 있는 쿠키의 Refresh Token 값을 반환하는 메서드 입니다.")
//    public ResponseEntity<? extends BaseResponseBody> reissue(
//            @CookieValue
//            @Nullable
//            @Parameter(required = false)
//            String token) {
//
//        return ResponseEntity
//                .ok()
//                .body(new BaseResponseBody<>(HttpStatus.OK, "Refresh Token fetched successfully", token));
//
//    }

}
