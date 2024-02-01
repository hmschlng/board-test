package com.ktds.board.board.api.dto.request;

import lombok.Builder;

public record ArticleListGetReq(
	Integer pageNum,
	Integer viewPerPage,
	Long categoryId) {
}
