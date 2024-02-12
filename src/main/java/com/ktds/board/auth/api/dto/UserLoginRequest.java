package com.ktds.board.auth.api.dto;

public record UserLoginRequest(
        String email,
        String password
) {
}
