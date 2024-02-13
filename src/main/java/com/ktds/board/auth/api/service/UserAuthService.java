package com.ktds.board.auth.api.service;

import com.ktds.board.auth.api.dto.request.UserLoginReq;
import com.ktds.board.auth.api.dto.request.UserRegisterReq;
import com.ktds.board.auth.api.dto.request.VerifyEmailReq;
import com.ktds.board.auth.db.entity.UserAuth;
import jakarta.mail.MessagingException;

public interface UserAuthService {
    Long saveOne(UserRegisterReq req);

    UserAuth login(UserLoginReq req);

    String checkEmailId(VerifyEmailReq req) throws MessagingException;
}
