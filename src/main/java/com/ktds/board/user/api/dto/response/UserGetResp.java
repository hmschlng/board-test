package com.ktds.board.user.api.dto.response;

import com.ktds.board.user.db.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserGetResp {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String profileImg;
    private LocalDateTime createdAt;

    @Builder
    public UserGetResp(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
        this.createdAt = user.getCreatedAt();
    }

}
