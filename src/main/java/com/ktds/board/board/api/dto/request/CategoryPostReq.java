package com.ktds.board.board.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoryPostReq(

	@Schema(description = "카테고리 이름", type = "String", example = "카테고리1")
	@NotBlank(message = "필수 입력 항목입니다. (String)")
	String name

) {
}
