package com.ktds.board.user.api.service.impl;

import com.github.f4b6a3.tsid.Tsid;
import com.ktds.board.user.api.dto.response.UserGetResp;
import com.ktds.board.user.db.entity.User;
import com.ktds.board.user.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.ktds.board.user.api.dto.request.UserPostReq;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserGetResp getOne(UserGetReq req) {

        var user = userRepository.findById(req.id())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        return UserGetResp.builder()
                .user(user)
                .build();
    }

    @Override
    public Long saveOne(UserPostReq req) {
        // 사용자 이메일 중복 조회
        var userExists = emailExists(req.email());

        // 사용자 닉네임 중복 조회
        var nicknameExists = nicknameExists(req.nickname());

        if(userExists)
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");

        if(nicknameExists)
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");

        return userRepository.save(User.builder()
                .id(Tsid.fast().toLong())
                .email(req.email())
                .nickname(req.nickname())
                .build()).getId();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean nicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


}
