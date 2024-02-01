package com.ktds.board.board.api.controller;

import java.net.URI;
import java.util.List;

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

import com.ktds.board.board.api.dto.CommentListGetResp;
import com.ktds.board.board.api.dto.request.CommentPostReq;
import com.ktds.board.board.api.dto.request.CommentPutReq;
import com.ktds.board.board.api.service.CommentService;
import com.ktds.board.common.entity.BaseResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {
	private final CommentService commentService;

	// 게시글의 댓글 모음 조회
	@GetMapping("/{articleId}")
	public ResponseEntity<? extends BaseResponseBody> getCommentList(
		@PathVariable("articleId") Long articleId
	) {
		var commentList = commentService.getAll(articleId);

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody(HttpStatus.OK, commentList));
	}

	// 내가 쓴 댓글 모음 조회
	// 추후 기간별 조회 구현
	@GetMapping("/myComment")
	public List<CommentListGetResp> getMyCommentList() {
		return null;
	}

	// 댓글 작성
	@PostMapping
	public ResponseEntity<? extends BaseResponseBody> createComment(
		@RequestBody @Valid CommentPostReq req,
		HttpServletRequest request
	) {
		var commentId = commentService.saveOne(req);
		var location = URI.create(request.getRequestURI() + "/" + req.articleId() + "/" + commentId);
		var successMessage = "댓글을 성공적으로 추가했습니다. (userID=" + req.userId() + ", articleId=" + req.articleId() + ")";

		return ResponseEntity
			.created(location)
			.body(new BaseResponseBody<>(HttpStatus.CREATED, successMessage));
	}

	// 댓글 수정
	@PutMapping
	public ResponseEntity<? extends BaseResponseBody> udpateComment(
		@RequestBody @Valid CommentPutReq req
	) {
		var commentId = commentService.updateOne(req);
		var successMessage = "댓글을 성공적으로 추가했습니다. (commentId=" + commentId + ")";

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody(HttpStatus.OK, successMessage));
	}

	// 댓글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<? extends BaseResponseBody> deleteComment(
		@PathVariable Long id
	) {
		var commentId = commentService.deleteById(id);
		var successMessage = "댓글을 성공적으로 삭제했습니다. (commentId=" + commentId + ")";

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody(HttpStatus.OK, successMessage));
	}
}