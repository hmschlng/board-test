package com.ktds.board.user.api.service.impl;

import com.github.f4b6a3.tsid.Tsid;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.dto.request.UserPostReq;
import com.ktds.board.user.api.dto.response.UserInfoGetResp;
import com.ktds.board.user.api.service.UserInfoService;
import com.ktds.board.user.db.entity.UserInfo;
import com.ktds.board.user.db.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserInfoGetResp getOne(UserGetReq req) {

        var userInfo = userInfoRepository.findById(req.id())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        return UserInfoGetResp.builder()
                .userInfo(userInfo)
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

        return userInfoRepository.save(UserInfo.builder()
                .id(Tsid.fast().toLong())
                .email(req.email())
                .nickname(req.nickname())
                .build()).getId();
    }

    @Override
    public boolean emailExists(String email) {
        return userInfoRepository.existsByEmail(email);
    }

    @Override
    public boolean nicknameExists(String nickname) {
        return userInfoRepository.existsByNickname(nickname);
    }

}
