package com.ktds.board.board.api.dto.request;

import lombok.Builder;
import lombok.Getter;

public record LikePostReq (
	Long id,
	Long userId,
	Long articleId,
	String type
) {
}
