package com.ktds.board.board.api.controller;

import java.net.URI;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.request.ArticlePostReq;
import com.ktds.board.board.api.dto.request.ArticlePutReq;
import com.ktds.board.board.api.dto.response.ArticleGetResp;
import com.ktds.board.board.api.service.ArticleService;
import com.ktds.board.common.entity.BaseResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    // 게시글 전체조회
    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getArticleList(
            ArticleListGetReq req) {
        var articleList = articleService.getAll(req);

        return ResponseEntity
                .ok()
                .body(new BaseResponseBody<>(HttpStatus.OK, articleList));
    }

    // 게시글 단건조회
    @GetMapping("/{id}")
    public ResponseEntity<? extends BaseResponseBody> getArticle(
        @PathVariable Long id
    ) {
        var article = articleService.getOne(id);

        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, article));
    }

    // 게시글 작설
    @PostMapping
    public ResponseEntity<?extends BaseResponseBody> createArticle(
        @RequestBody @Valid ArticlePostReq req,
        HttpServletRequest request
    ) {
        var articleId = articleService.saveOne(req);
        var location = URI.create(request.getRequestURI() + "/" + articleId);
        var successMessage = "게시글을 성공적으로 생성했습니다. (userId=" + req.userId() + ", articleId=" + articleId + ")";

        return ResponseEntity
            .created(location)
            .body(new BaseResponseBody<>(HttpStatus.CREATED, successMessage));
    }

    // 게시글 수정
    @PutMapping
    public ResponseEntity<? extends BaseResponseBody> updateArticle(
        @RequestBody @Valid ArticlePutReq req
    ) {
        var articleId = articleService.updateOne(req);
        var successMessage = "게시글을 성공적으로 수정했습니다. (articleId=" + articleId + ")";

        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, successMessage));
    }

    // 게시글 삭제
    @DeleteMapping
    public ResponseEntity<? extends BaseResponseBody> deleteArticle(
        @PathVariable Long id
    ) {
        var articleId = articleService.deleteById(id);

        var successMessage = "게시글을 성공적으로 삭제했습니다. (articleId=" + articleId + ")";
        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, successMessage));
    }

}
