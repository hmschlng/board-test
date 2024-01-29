package com.ktds.board.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktds.board.db.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	//
	// Optional<Article> getArticle(Long id);
	// Optional<List<Article>> getAllArticles();
	// void deleteArticle(Long id);
}
