package com.ktds.board.auth.api.service.impl;

import com.github.f4b6a3.tsid.Tsid;
import com.ktds.board.auth.api.dto.UserLoginRequest;
import com.ktds.board.auth.api.dto.UserRegisterRequest;
import com.ktds.board.auth.api.service.UserAuthService;
import com.ktds.board.auth.db.entity.UserAuth;
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
                .id(Tsid.fast().toLong())
                .email(req.email())
                .password(bCryptPasswordEncoder.encode(req.password()))
                .build();

        var userInfo = UserInfo.builder()
                .id(userAuth.getId())
                .email(req.email())
                .nickname(req.nickname())
                .build();

        userAuthRepository.save(userAuth);
        userInfoRepository.save(userInfo);

        return userInfo.getId();
    }

    public boolean login(UserLoginRequest req) {
        var userAuth = userAuthRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        return bCryptPasswordEncoder.matches(req.password(), userAuth.getPassword());
    }
}
