package com.ktds.board.user.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.common.annotation.ApiDocumentResponse;
import com.ktds.board.common.entity.BaseResponseBody;
import com.ktds.board.user.api.dto.request.UserEmailExistsReq;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.dto.request.UserNicknameExistsReq;
import com.ktds.board.user.api.dto.request.UserPostReq;
import com.ktds.board.user.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자 관리 API", description = "사용자 정보를 조회, 추가, 수정, 삭제하는 API입니다.")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

	private final UserService userService;

	@ApiDocumentResponse
	@Operation(summary = "getUser", description="사용자 정보 조회")
	@PostMapping("/user")
	public ResponseEntity<? extends BaseResponseBody> getUser(
		@RequestBody @Valid UserGetReq req) {
		var user = userService.getOne(req);

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody<>(HttpStatus.OK, user));
	}

	@ApiDocumentResponse
	@Operation(summary = "createUser", description="사용자 추가")
	@PostMapping
	public ResponseEntity<? extends BaseResponseBody> createUser(
		@RequestBody @Valid UserPostReq req,
		HttpServletRequest request) {

		var userId = userService.saveOne(req);
		var location = URI.create(request.getRequestURI() + "/" + userId);
		var successMessage = "사용자 계정을 성공적으로 생성했습니다. (userId=" + userId + ")";

		return ResponseEntity
			.created(location)
			.body(new BaseResponseBody<>(HttpStatus.CREATED, successMessage));
	}

	@ApiDocumentResponse
	@Operation(summary = "isUserEmailExists", description="이메일 중복체크")
	@PostMapping("/exists/email")
	public ResponseEntity<? extends BaseResponseBody> isUserEmailExists(
		@RequestBody @Valid UserEmailExistsReq req) {

		var exists =  userService.emailExists(req.email());

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody<>(HttpStatus.OK, exists));
	}

	@ApiDocumentResponse
	@Operation(summary = "isUserNicknameExists", description="닉네임 중복체크")
	@PostMapping("/exists/nickname")
	public ResponseEntity<? extends BaseResponseBody> isUserNicknameExists(
			@RequestBody @Valid UserNicknameExistsReq req) {

		var exists =  userService.nicknameExists(req.nickname());

		return ResponseEntity
				.ok()
				.body(new BaseResponseBody<>(HttpStatus.OK, exists));
	}

}
