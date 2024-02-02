package com.ktds.board.board.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

public record ArticlePostReq(
	@Schema(description = "카테고리 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long categoryId,
	@Schema(description = "사용자 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long userId,
	@Schema(description = "게시글 제목", type = "String", example = "게시글 제목1")
	String title,
	@Schema(description = "게시글 내용", type = "String", example = "개시글 내용1")
	String content
) {
}
