package com.ktds.board.board.api.dto.request;

import lombok.Builder;

public record ArticlePutReq(
	Long id,
	String title,
	String content
) {
}
