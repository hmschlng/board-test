package com.ktds.board.board.api.dto.request;

import com.ktds.board.board.db.entity.enumtype.LikeContentType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

public record LikePostReq (

	@Schema(description = "좋아요 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long id,

	@Schema(description = "좋아요 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long userId,

	@Schema(description = "좋아요 식별자", type = "Long", example = "1")
	@Positive(message = "필수 입력항목입니다. (양수)")
	@Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
	Long articleId,

	@Schema(description = "좋아요한 컨텐츠 유형", type = "Enum", example = "ARTICLE")
	@Positive(message = "필수 입력항목입니다. (String)")
	LikeContentType type
) {
}
