package com.ktds.board.board.db.repository;

import com.ktds.board.board.db.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	//
	// Optional<Article> getArticle(Long id);
	// Optional<List<Article>> getAllArticles();
	// void deleteArticle(Long id);
}
