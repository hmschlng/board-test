package com.ktds.board.auth.db.repository;

import com.ktds.board.auth.db.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByEmail(String email);
    Optional<UserAuth> findByKakaoOAuthId(String kakaoOAuthId);
    Optional<UserAuth> findByGoogleOAuthId(String googleOAuthId);
    Optional<UserAuth> findByNaverOAuthId(String naverOAuthId);

    boolean existsByEmail(String email);
}
