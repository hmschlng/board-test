package com.ktds.board.board.api.service;

import com.ktds.board.board.api.dto.request.LikePostReq;

public interface LikeService {
	Object saveOne(LikePostReq req);

	Long deleteById(Long id);
}
