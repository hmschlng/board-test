package com.ktds.board.board.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.board.api.dto.request.LikePostReq;
import com.ktds.board.board.api.service.LikeService;
import com.ktds.board.common.swagger.annotation.ApiDocumentResponse;
import com.ktds.board.common.response.BaseResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "좋아요 관리 API", description = "댓글이나 게시글에 누르는 좋아요 기능과 관련된 API입니다.")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/board/like")
@RestController
public class LikeController {
	private LikeService likeService;

	// 게시글이나 댓글에 좋아요 누르기(생성)
	@ApiDocumentResponse
	@Operation(summary = "createLike", description="게시글이나 댓글에 좋아요 누르기(생성)")
	@PostMapping
	public ResponseEntity<? extends BaseResponseBody> createLike(
		@RequestBody @Valid LikePostReq req,
		HttpServletRequest request
	) {

		var likeId = likeService.saveOne(req);

		var location = URI.create(request.getRequestURI() + "/" + likeId);
		var successMessage = "좋아요를 성공적으로 추가했습니다. (type=" + req.type() + ", userID=" + req.userId() + ")";

		return ResponseEntity
			.created(location)
			.body(new BaseResponseBody<>(HttpStatus.CREATED, successMessage));
	}

	// 누른 좋아요를 취소하기(삭제)
	@ApiDocumentResponse
	@Operation(summary = "deleteLike", description="누른 좋아요를 취소하기(삭제)")
	@DeleteMapping("/{id}")
	public ResponseEntity<? extends BaseResponseBody> deleteLike(
		@PathVariable Long id
	) {
		var likeId = likeService.deleteById(id);

		// 좋아요를 취소하면
		//   좋아요 기록을 삭제?
		//   사용자의 좋아요 기록을 삭제?
		return ResponseEntity
			.ok()
			.body(new BaseResponseBody<>(HttpStatus.OK, null/*successMessage*/));
	}
}
