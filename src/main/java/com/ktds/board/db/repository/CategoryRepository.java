package com.ktds.board.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktds.board.db.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
