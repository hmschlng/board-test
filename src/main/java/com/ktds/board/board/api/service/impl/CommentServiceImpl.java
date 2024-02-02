package com.ktds.board.board.api.service.impl;

import java.util.List;

import com.ktds.board.board.api.dto.request.CommentListGetResp;
import com.ktds.board.board.api.dto.request.CommentPostReq;
import com.ktds.board.board.api.dto.request.CommentPutReq;
import com.ktds.board.board.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
	@Override
	public List<CommentListGetResp> getAll(Long articleId) {
		return null;
	}

	@Override
	public Long saveOne(CommentPostReq req) {
		return null;
	}

	@Override
	public Long updateOne(CommentPutReq req) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}

}
