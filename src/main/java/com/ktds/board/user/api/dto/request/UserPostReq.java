package com.ktds.board.user.api.dto.request;

public record UserPostReq(
	String email,
	String name,
	String nickname
) {
}
