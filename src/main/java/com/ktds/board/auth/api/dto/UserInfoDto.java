package com.ktds.board.auth.api.dto;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    private Long userId; // UUID
    private String nickname;
    private String email;
    private String image;

}
