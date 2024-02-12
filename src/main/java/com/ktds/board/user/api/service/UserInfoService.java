package com.ktds.board.user.api.service;

import com.ktds.board.user.api.dto.request.UserPostReq;
import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.dto.response.UserInfoGetResp;

public interface UserInfoService {
	UserInfoGetResp getOne(UserGetReq req);

	Long saveOne(UserPostReq req);

	boolean emailExists(String email);

	boolean nicknameExists(String nickname);

}
