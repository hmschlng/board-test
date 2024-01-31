package com.ktds.board.board.api.dto.request;

import lombok.Builder;

@Builder
public record GetArticleList(
	Integer pageNum,
	Integer viewPerPage,
	Long categoryId) {
}
