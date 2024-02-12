package com.ktds.board.auth.api.controller;

import com.ktds.board.auth.api.dto.UserLoginRequest;
import com.ktds.board.auth.api.dto.UserRegisterRequest;
import com.ktds.board.auth.api.service.UserAuthService;
import com.ktds.board.common.auth.util.JwtTokenProvider;
import com.ktds.board.common.response.BaseResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<? extends BaseResponseBody> signup(@RequestBody UserRegisterRequest req) {

        log.info("진입!");
        userAuthService.saveOne(req);

        return ResponseEntity
                .ok()
                .body(new BaseResponseBody(HttpStatus.OK, "success"));
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody UserLoginRequest req) {
        var success = userAuthService.login(req);
        return ResponseEntity.ok().body("로그인 성공: " + success);
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
