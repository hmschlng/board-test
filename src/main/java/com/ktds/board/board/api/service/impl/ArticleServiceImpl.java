package com.ktds.board.board.api.service.impl;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.request.ArticlePostReq;
import com.ktds.board.board.api.dto.request.ArticlePutReq;
import com.ktds.board.board.api.dto.response.ArticleGetResp;
import com.ktds.board.board.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public List<ArticleGetResp> getAll(ArticleListGetReq req) {
        return null;
    }

    @Override
    public ArticleGetResp getOne(Long id) {
        return null;
    }

    @Override
    public Long saveOne(ArticlePostReq req) {
        return null;
    }

    @Override
    public Long updateOne(ArticlePutReq req) {
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }
}
