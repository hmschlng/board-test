package com.ktds.board.board.api.controller;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.response.ArticleGetResp;
import com.ktds.board.board.api.service.ArticleService;
import com.ktds.board.common.entity.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getArticleList(
            ArticleListGetReq req) {
        var articleList = articleService.getAll(req);

        return ResponseEntity
                .ok()
                .body(new BaseResponseBody<>(HttpStatus.OK, articleList));
    }

    @GetMapping("/detail")
    public ResponseEntity<? extends BaseResponseBody> getArticle(
            ArticleGetResp
    )

}
