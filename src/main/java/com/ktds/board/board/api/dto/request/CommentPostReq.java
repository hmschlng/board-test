package com.ktds.board.board.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record CommentPostReq (

	@Schema(description = "카테고리 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long articleId,

	@Schema(description = "사용자 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long userId,

	@Schema(description = "댓글 내용", type = "String", example = "댓글 내용1")
	@NotBlank(message = "필수 입력항목입니다. (String)")
	String content,

	@Nullable
	@Schema(description = "부모 댓글 식별자", type = "Long", example = "null", nullable = true)
	Long parentId

) {
}
