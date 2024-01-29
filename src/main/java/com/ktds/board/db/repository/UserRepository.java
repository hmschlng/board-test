package com.ktds.board.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ktds.board.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//
	// Optional<User> getUser(Long id);
	//
	// Optional<List<User>> getAllUsers();
}
