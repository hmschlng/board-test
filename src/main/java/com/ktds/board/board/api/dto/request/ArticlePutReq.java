package com.ktds.board.board.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

public record ArticlePutReq(
	@Schema(description = "수정할 게시글 Id", example = "1")
	@Positive(message = "필수 입력 항목입니다. (양수)")
	Long articleId,

	@Schema(description = "수정할 게시글 Id", example = "1")
	@NotBlank(message = "필수 입력항목입니다. (String)")
	String title,

	@Schema(description = "수정할 게시글 Id", example = "1")
	@NotBlank(message = "필수 입력항목입니다. (String)")
	String content
) {
}
