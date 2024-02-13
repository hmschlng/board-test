package com.ktds.board.auth.api.service;

import com.ktds.board.auth.api.dto.UserLoginRequest;
import com.ktds.board.auth.api.dto.UserRegisterRequest;
import com.ktds.board.auth.db.entity.UserAuth;

public interface UserAuthService {
    Long saveOne(UserRegisterRequest req);
    UserAuth login(UserLoginRequest req);
}
