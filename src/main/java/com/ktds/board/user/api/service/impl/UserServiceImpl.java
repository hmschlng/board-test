package com.ktds.board.user.api.service.impl;

import org.springframework.stereotype.Service;

import com.ktds.board.user.api.dto.request.UserGetReq;
import com.ktds.board.user.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	@Override
	public Object getOne(UserGetReq req) {
		return null;
	}
}
