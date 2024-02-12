package com.ktds.board.auth.api.service;

import com.ktds.board.auth.api.dto.UserLoginRequest;
import com.ktds.board.auth.api.dto.UserRegisterRequest;

public interface UserAuthService {
    Long saveOne(UserRegisterRequest req);
    boolean login(UserLoginRequest req);
}
