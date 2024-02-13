package com.ktds.board.auth.api.service.impl;

import com.github.f4b6a3.tsid.Tsid;
import com.ktds.board.auth.api.dto.UserLoginRequest;
import com.ktds.board.auth.api.dto.UserRegisterRequest;
import com.ktds.board.auth.api.service.UserAuthService;
import com.ktds.board.auth.db.entity.UserAuth;
import com.ktds.board.auth.db.entity.enumtype.Role;
import com.ktds.board.auth.db.repository.UserAuthRepository;
import com.ktds.board.user.db.entity.UserInfo;
import com.ktds.board.user.db.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserAuthRepository userAuthRepository;
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long saveOne(UserRegisterRequest req) {
        var userAuth = UserAuth.builder()
                .email(req.email())
                .password(bCryptPasswordEncoder.encode(req.password()))
                .role(Role.valueOf(req.role()))
                .build();

        userAuth = userAuthRepository.save(userAuth);

        var userInfo = UserInfo.builder()
                .id(userAuth.getId())
                .email(req.email())
                .nickname(req.nickname())
                .build();

        userInfoRepository.save(userInfo);

        return userInfo.getId();
    }

    public UserAuth login(UserLoginRequest req) {
        var userAuth = userAuthRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 사용자 정보입니다. 아이디/비밀번호를 확인해주세요."));

        var match = bCryptPasswordEncoder.matches(req.password(), userAuth.getPassword());

        if (!match) {
            throw new IllegalArgumentException("올바르지 않은 사용자 정보입니다. 아이디/비밀번호를 확인해주세요.");
        }

        return userAuth;
    }
}
