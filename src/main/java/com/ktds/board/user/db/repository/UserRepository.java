package com.ktds.board.user.db.repository;

import com.ktds.board.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//
	// Optional<User> getUser(Long id);
	//
	// Optional<List<User>> getAllUsers();
}
