package com.ktds.board.board.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.board.api.dto.request.LikePostReq;
import com.ktds.board.board.api.service.LikeService;
import com.ktds.board.common.entity.BaseResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/like")
@RestController
public class LikeController {
	private LikeService likeService;

	// 게시글이나 댓글에 좋아요를 누른 정보를 저장하기(생성)
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
