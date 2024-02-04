package com.ktds.board.user.api.controller;

import com.ktds.board.common.annotation.ApiDocumentResponse;
import com.ktds.board.common.entity.BaseResponseBody;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.dto.request.UserPostReq;
import com.ktds.board.user.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "사용자 관리 API", description = "사용자 정보를 조회, 추가, 수정, 삭제하는 API입니다.")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

	private final UserService userService;

	@ApiDocumentResponse
	@Operation(summary = "getUser", description="사용자 정보 조회")
	@GetMapping
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
	@GetMapping("/exists/email/{email}")
	public ResponseEntity<? extends BaseResponseBody> isUserEmailExists(
			@PathVariable("email")
			@NotBlank(message = "필수 입력 항목입니다.")
			@Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞지 않습니다.")
			String email) {

		var exists =  userService.emailExists(email);

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody<>(HttpStatus.OK, exists));
	}

	@ApiDocumentResponse
	@Operation(summary = "isUserNicknameExists", description="닉네임 중복체크")
	@GetMapping("/exists/nickname/{nickname}")
	public ResponseEntity<? extends BaseResponseBody> isUserNicknameExists(
			@Schema(description = "닉네임", type = "String", example = "nickname1")
			@NotBlank(message = "필수 입력 항목입니다.")
			@PathVariable("nickname") String nickname) {

		var exists =  userService.nicknameExists(nickname);

		return ResponseEntity
				.ok()
				.body(new BaseResponseBody<>(HttpStatus.OK, exists));
	}


}
