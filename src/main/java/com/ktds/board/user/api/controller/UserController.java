package com.ktds.board.user.api.controller;

import java.net.URI;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.common.entity.BaseResponseBody;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.service.UserService;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<? extends BaseResponseBody> getUser(
		@RequestBody @Valid UserGetReq req) {
		var user = userService.getOne(req);

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody(HttpStatus.OK, user));
	}

	@PostMapping
	public ResponseEntity<? extends BaseResponseBody> createUser(
		@RequestBody @Valid UserPostReq req,
		HttpServletRequest request) {

		var userId = userService.saveOne(req);
		var location = URI.create(request.getRequestURI() + "/" + userId);
		var successMessage = "사용자 계정을 성공적으로 생성했습니다. (userId=" + userId + ")";

		return ResponseEntity
			.created(location)
			.body(new BaseResponseBody(HttpStatus.CREATED, successMessage));
	}

}
