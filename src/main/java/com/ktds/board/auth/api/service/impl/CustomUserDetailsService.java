package com.ktds.board.auth.api.service.impl;

import com.ktds.board.auth.db.entity.CustomUserDetails;
import com.ktds.board.auth.db.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userAuth = userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 email 정보를 찾을 수 없습니다."));

        return new CustomUserDetails(userAuth);
    }
}
