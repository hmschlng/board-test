package com.ktds.board.auth.api.dto;

public record UserRegisterRequest(
        String email,
        String password,
        String nickname,
		String role
) {
}
