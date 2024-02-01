package com.ktds.board.board.api.dto.request;

import lombok.Builder;

public record CommentPostReq (
	Long id,
	Long articleId,
	Long userId,
	String content,
	Long parentId
) {
}
