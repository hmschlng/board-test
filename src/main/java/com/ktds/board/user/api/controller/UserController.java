package com.ktds.board.user.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.common.entity.BaseResponseBody;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.service.UserService;

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


}
