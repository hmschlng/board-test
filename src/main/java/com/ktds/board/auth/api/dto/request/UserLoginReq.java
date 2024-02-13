package com.ktds.board.auth.api.dto.request;

public record UserLoginReq(
        String email,
        String password
) {
}
