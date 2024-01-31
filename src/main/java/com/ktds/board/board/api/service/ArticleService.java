package com.ktds.board.board.api.service;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.response.ArticleGetResp;

import java.util.List;

public interface ArticleService {
    List<ArticleGetResp> getAll(ArticleListGetReq req);
}
