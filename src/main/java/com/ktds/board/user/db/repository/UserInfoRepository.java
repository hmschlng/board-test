package com.ktds.board.user.db.repository;

import com.ktds.board.user.db.entity.User;
import com.ktds.board.user.db.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String nickname);
}
