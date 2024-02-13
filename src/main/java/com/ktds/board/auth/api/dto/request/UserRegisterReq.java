package com.ktds.board.auth.api.dto.request;

public record UserRegisterReq(
        String email,
        String password,
        String nickname,
		String role
) {
}
