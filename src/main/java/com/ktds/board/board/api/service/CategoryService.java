package com.ktds.board.board.api.service;

import com.ktds.board.board.api.dto.request.CategoryPostReq;
import com.ktds.board.board.api.dto.response.CategoryGetResp;

public interface CategoryService {
	CategoryGetResp getOne(Long categoryId);

	Long saveOne(CategoryPostReq req);
}
