package com.ktds.board.board.api.service;

import com.ktds.board.board.api.dto.request.GetArticleList;
import com.ktds.board.board.api.dto.response.GetArticle;

import java.util.List;

public interface ArticleService {
    List<GetArticle> getArticleList(GetArticleList req);
}
