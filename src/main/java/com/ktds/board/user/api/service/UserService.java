package com.ktds.board.user.api.service;

import com.ktds.board.user.api.controller.UserPostReq;
import com.ktds.board.user.api.dto.request.UserGetReq;

public interface UserService {
	Object getOne(UserGetReq req);

	Object saveOne(UserPostReq req);
}
