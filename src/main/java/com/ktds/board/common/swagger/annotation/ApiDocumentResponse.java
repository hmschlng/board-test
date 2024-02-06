package com.ktds.board.common.swagger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static com.ktds.board.common.swagger.HttpStatusCodeConstants.*;

import com.ktds.board.common.exception.handler.BaseRuntimeExceptionHandler;
import com.ktds.board.common.exception.handler.ValidationExceptionHandler;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {

	@ApiResponse(responseCode = OK+"", description = "API 호출 성공"),

	@ApiResponse(responseCode = CREATED+"", description = "생성 성공"),

	@ApiResponse(
		responseCode = NOT_FOUND+"",
		description = "존재하지 않는 API",
		content = @Content(schema = @Schema(implementation = BaseRuntimeExceptionHandler.class))),

	@ApiResponse(
		responseCode = BAD_REQUEST+"",
		description = "유효성 검증 실패",
		content = @Content(schema = @Schema(implementation = ValidationExceptionHandler.class))),

	@ApiResponse(
		responseCode = METHOD_NOT_ALLOWED+"",
		description = "잘못된 Method 요청",
		content = @Content(schema = @Schema(implementation = BaseRuntimeExceptionHandler.class))),

	@ApiResponse(
		responseCode = UNAUTHORIZED+"",
		description = "인증 실패",
		content = @Content(schema = @Schema(implementation = BaseRuntimeExceptionHandler.class))),

	@ApiResponse(
		responseCode = FORBIDDEN+"",
		description = "인가 실패(권한 없음)",
		content = @Content(schema = @Schema(implementation = BaseRuntimeExceptionHandler.class))),

	@ApiResponse(
		responseCode = METHOD_NOT_ALLOWED+"",
		description = "데이터 등록 실패",
		content = @Content(schema = @Schema(implementation = BaseRuntimeExceptionHandler.class))),

})
public @interface ApiDocumentResponse {
}