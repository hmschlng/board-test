package com.ktds.board.auth.api.service.impl;

import com.ktds.board.auth.api.dto.request.UserLoginReq;
import com.ktds.board.auth.api.dto.request.UserRegisterReq;
import com.ktds.board.auth.api.dto.request.VerifyEmailReq;
import com.ktds.board.auth.api.service.UserAuthService;
import com.ktds.board.auth.api.util.MailDispatcher;
import com.ktds.board.auth.db.entity.UserAuth;
import com.ktds.board.auth.db.entity.enumtype.Role;
import com.ktds.board.auth.db.repository.UserAuthRepository;
import com.ktds.board.user.db.entity.UserInfo;
import com.ktds.board.user.db.repository.UserInfoRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserAuthRepository userAuthRepository;
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailDispatcher mailDispatcher;

    @Override
    public Long saveOne(UserRegisterReq req) {
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

    public UserAuth login(UserLoginReq req) {
        var userAuth = userAuthRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 사용자 정보입니다. 아이디/비밀번호를 확인해주세요."));

        var match = bCryptPasswordEncoder.matches(req.password(), userAuth.getPassword());

        if (!match) {
            throw new IllegalArgumentException("올바르지 않은 사용자 정보입니다. 아이디/비밀번호를 확인해주세요.");
        }

        return userAuth;
    }

    @Override
    public String checkEmailId(VerifyEmailReq req) throws MessagingException {
        var exists = userAuthRepository.existsByEmail(req.email());

        if(exists) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return mailDispatcher.sendAuthMail(req.email());
    }
}
