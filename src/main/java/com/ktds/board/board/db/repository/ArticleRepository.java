package com.ktds.board.board.db.repository;

import java.util.List;
import java.util.Optional;

import com.ktds.board.board.db.entity.Article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	//
	// Optional<Article> getArticle(Long id);
	// Optional<List<Article>> getAllArticles();
	// void deleteArticle(Long id);
	default List<Article> findAllByCategoryId(Long categoryId, Pageable pageable) {
		return this.findArticlesByCategory_IdOrderByCreatedAtDesc(categoryId, pageable);
	}

	default List<Article> findAllByUserId(Long userId) {
		return this.findArticlesByUserInfo_IdOrderByCreatedAtDesc(userId);
	}

	List<Article> findArticlesByCategory_IdOrderByCreatedAtDesc(Long categoryId, Pageable pageable);

	List<Article> findArticlesByUserInfo_IdOrderByCreatedAtDesc(Long userId);
}
