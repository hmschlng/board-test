package com.ktds.board.board.db.repository;

import java.util.List;
import java.util.Optional;

import com.ktds.board.board.db.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	//
	// Optional<Article> getArticle(Long id);
	// Optional<List<Article>> getAllArticles();
	// void deleteArticle(Long id);
	default Optional<List<Article>> findAllByCategoryId(Long categoryId) {
		return this.findArticlesByCategory_IdOrderByCreatedAtDesc(categoryId);
	}

	Optional<List<Article>> findArticlesByCategory_IdOrderByCreatedAtDesc(Long categoryId);
}
