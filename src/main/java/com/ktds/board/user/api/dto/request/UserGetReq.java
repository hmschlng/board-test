package com.ktds.board.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record UserGetReq(
        @Schema(description = "사용자 식별자", type = "Long", example = "1")
        @Positive(message = "필수 입력항목입니다. (양수)")
        @Max(value = Long.MAX_VALUE, message = "id 범위를 벗어났습니다.")
        Long id
) {
}
