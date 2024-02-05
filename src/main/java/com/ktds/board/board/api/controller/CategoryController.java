package com.ktds.board.board.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.board.board.api.dto.request.CategoryPostReq;
import com.ktds.board.board.api.service.CategoryService;
import com.ktds.board.common.annotation.ApiDocumentResponse;
import com.ktds.board.common.entity.BaseResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Tag(name = "카테고리 관리 API", description = "카테고리를 조회하는 API입니다.")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/category")
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	// @Hidden
	@ApiDocumentResponse
	@Operation(summary = "getCategory", description="카테고리 조회")
	@GetMapping("/{catergeryId}")
	public ResponseEntity<? extends BaseResponseBody> getCategory(
		@Positive(message = "필수 입력항목입니다. (양수)")
		@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
		@PathVariable("catergeryId") Long categoryId
	) {
		var category = categoryService.getOne(categoryId);

		return ResponseEntity
			.ok()
			.body(new BaseResponseBody<>(HttpStatus.OK, category));
	}

	@ApiDocumentResponse
	@Operation(summary = "createCategory", description="카테고리 생성")
	@PostMapping
	public ResponseEntity<? extends BaseResponseBody> createCategory(
		@RequestBody @Valid CategoryPostReq req,
		HttpServletRequest request
	) {
		var categoryId = categoryService.saveOne(req);
		var location = URI.create(request.getRequestURI() + "/" + categoryId);
		var successMessage = "카테고리를 성공적으로 추가했습니다. (categoryId=" + categoryId + ")";

		return ResponseEntity
			.created(location)
			.body(new BaseResponseBody<>(HttpStatus.CREATED, successMessage));
	}


}
