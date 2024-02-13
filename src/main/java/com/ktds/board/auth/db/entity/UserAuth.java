package com.ktds.board.auth.db.entity;

import com.ktds.board.auth.db.entity.enumtype.Role;
import com.ktds.board.common.audit.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@Table(name = "user_auth")
@Entity
public class UserAuth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Column
    private String password;

    @Column(name = "oauth2_kakao_id")
    private String kakaoOAuthId;

    @Column(name = "oauth2_google_id")
    private String googleOAuthId;

    @Column(name = "oauth2_naver_id")
    private String naverOAuthId;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
}
