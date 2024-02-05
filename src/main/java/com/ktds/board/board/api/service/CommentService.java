package com.ktds.board.board.api.service;

import com.ktds.board.board.api.dto.response.CommentListGetResp;
import com.ktds.board.board.api.dto.request.CommentPostReq;
import com.ktds.board.board.api.dto.request.CommentPutReq;

import java.util.List;

public interface CommentService {

	List<CommentListGetResp> getAll(Long articleId);

	List<CommentListGetResp> getAllByUserId(Long userId);

	Long saveOne(CommentPostReq req);

	Long updateOne(CommentPutReq req);

	Long deleteById(Long id);

}
