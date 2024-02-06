package com.ktds.board.common.auth.enumtype;

import lombok.Getter;

@Getter
public enum JwtTokenStatusCode {
	DENIED, ACCESSED, EXPIRED;
}