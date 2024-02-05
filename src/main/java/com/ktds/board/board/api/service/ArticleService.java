package com.ktds.board.board.api.service;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.request.ArticlePostReq;
import com.ktds.board.board.api.dto.request.ArticlePutReq;
import com.ktds.board.board.api.dto.response.ArticleGetResp;

import java.util.List;

public interface ArticleService {
	List<ArticleGetResp> getAll(ArticleListGetReq req);

	List<ArticleGetResp> getAllByUserId(Long id);

	ArticleGetResp getOne(Long articleId);

	Long saveOne(ArticlePostReq req);

	Long updateOne(ArticlePutReq req);

	Long deleteById(Long articleId);

}
