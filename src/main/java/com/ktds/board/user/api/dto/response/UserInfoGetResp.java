package com.ktds.board.user.api.dto.response;

import com.ktds.board.user.db.entity.UserInfo;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserInfoGetResp {
    private Long id;
    private String email;
    private String nickname;
    private String profileImg;
    private LocalDateTime createdAt;
    private String token;

    @Builder
    public UserInfoGetResp(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.email = userInfo.getEmail();
        this.nickname = userInfo.getNickname();
        this.profileImg = userInfo.getProfileImg();
        this.createdAt = userInfo.getCreatedAt();
    }

    public void setToken(String token) {
        this.token = token;
    }
}
