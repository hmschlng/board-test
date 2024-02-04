//package com.ktds.board.user.api.dto.request;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//
//public record UserEmailExistsReq (
//        @Schema(description = "사용자 이메일", type = "String", example = "example@mail.com")
//        @NotBlank(message = "필수 입력 항목입니다.")
//        @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞지 않습니다.")
//        String email
//) {
//}
