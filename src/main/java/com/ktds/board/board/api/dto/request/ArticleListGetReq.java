package com.ktds.board.board.api.dto.request;

import lombok.Builder;

@Builder
public record ArticleListGetReq(
	Integer pageNum,
	Integer viewPerPage,
	Long categoryId) {
}
