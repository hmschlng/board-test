package com.ktds.board.board.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.request.ArticlePostReq;
import com.ktds.board.board.api.dto.request.ArticlePutReq;
import com.ktds.board.board.api.service.ArticleService;
import com.ktds.board.common.swagger.annotation.ApiDocumentResponse;
import com.ktds.board.common.response.BaseResponseBody;
import com.ktds.board.user.api.dto.request.UserGetReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Tag(name = "게시글 관리 API", description = "게시글을 조회, 추가, 수정, 삭제하는 API입니다.")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/board/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    // 게시글 전체조회
    @ApiDocumentResponse
    @Operation(summary = "getArticleList", description="게시글 전체 조회")
    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> getArticleList(
            ArticleListGetReq req) {
        var articleList = articleService.getAll(req);

        return ResponseEntity
                .ok()
                .body(new BaseResponseBody<>(HttpStatus.OK, articleList));
    }

    // 내가 쓴 게시글 전체 조회
    @ApiDocumentResponse
    @Operation(summary = "getMyArticleList", description="내가 쓴 게시글 전체 조회")
    @PostMapping("/me")
    public ResponseEntity<? extends BaseResponseBody> getMyArticleList(
        @RequestBody @Valid UserGetReq req
    ) {
        var articles = articleService.getAllByUserId(req.id());

        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, articles));
    }

    // 게시글 단건조회
    @ApiDocumentResponse
    @Operation(summary = "getArticle", description="게시글 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<? extends BaseResponseBody> getArticle(
        @PathVariable Long id
    ) {
        var article = articleService.getOne(id);

        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, article));
    }

    // 게시글 작성
    @ApiDocumentResponse
    @Operation(summary = "createArticle", description="게시글 작성")
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
    @ApiDocumentResponse
    @Operation(summary = "updateArticle", description="게시글 수정")
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
    @ApiDocumentResponse
    @Operation(summary = "deleteArticle", description="게시글 삭제")
    @DeleteMapping("/{articleId}")
    public ResponseEntity<? extends BaseResponseBody> deleteArticle(
        @PathVariable("articleId") Long id
    ) {
        var articleId = articleService.deleteById(id);

        var successMessage = "게시글을 성공적으로 삭제했습니다. (articleId=" + articleId + ")";
        return ResponseEntity
            .ok()
            .body(new BaseResponseBody<>(HttpStatus.OK, successMessage));
    }

}
