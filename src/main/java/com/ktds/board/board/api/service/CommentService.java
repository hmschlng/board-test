package com.ktds.board.board.api.service;

import java.util.List;

import com.ktds.board.board.api.dto.request.CommentListGetResp;
import com.ktds.board.board.api.dto.request.CommentPostReq;
import com.ktds.board.board.api.dto.request.CommentPutReq;

public interface CommentService {
	List<CommentListGetResp> getAll(Long articleId);

	Long saveOne(CommentPostReq req);

	Long updateOne(CommentPutReq req);

	Long deleteById(Long id);
}
