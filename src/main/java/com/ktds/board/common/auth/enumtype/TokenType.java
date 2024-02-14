package com.ktds.board.common.auth.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
	ACCESS_TOKEN("Authentication"), REFRESH_TOKEN("refreshToken");

	private String key;

	TokenType(String key) {
		this.key = key;
	}
}
