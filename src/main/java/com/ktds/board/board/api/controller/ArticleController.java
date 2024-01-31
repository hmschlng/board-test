package com.ktds.board.board.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ktds.board.board.api.dto.request.GetArticleList;
import com.ktds.board.board.api.service.ArticleService;
import com.ktds.board.common.entity.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/")
    public ResponseEntity<? extends BaseResponseBody> getArticleList(
            GetArticleList req) throws JsonProcessingException {
        var articleList = articleService.getArticleList(req);

        return ResponseEntity
                .ok()
                .body(new BaseResponseBody<>(200, "OK", articleList));
    }

}
