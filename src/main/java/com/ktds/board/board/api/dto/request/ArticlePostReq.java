package com.ktds.board.board.api.dto.request;

import lombok.Builder;

public record ArticlePostReq(
	Long id,
	Long categoryId,
	Long userId,
	String title,
	String content
) {
}
