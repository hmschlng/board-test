package com.ktds.board.board.api.service.impl;

import com.ktds.board.board.api.dto.request.ArticleListGetReq;
import com.ktds.board.board.api.dto.request.ArticlePostReq;
import com.ktds.board.board.api.dto.request.ArticlePutReq;
import com.ktds.board.board.api.dto.response.ArticleGetResp;
import com.ktds.board.board.api.service.ArticleService;
import com.ktds.board.board.db.entity.Article;
import com.ktds.board.board.db.entity.Category;
import com.ktds.board.board.db.repository.ArticleRepository;
import com.ktds.board.board.db.repository.CategoryRepository;
import com.ktds.board.user.db.entity.User;
import com.ktds.board.user.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public List<ArticleGetResp> getAll(ArticleListGetReq req) {
		// 카테고리 아이디 기준으로 게시글 조회
		var articleList = articleRepository.findAllByCategoryId(req.categoryId())
			.orElseThrow(() -> new IllegalArgumentException("잘못된 유형의 카테고리입니다."));

		// 없을 경우 예외 테스트 필요

		// DTO List로 반환하기
		return articleList.stream().map(article ->
			ArticleGetResp.builder()
				.article(article)
				.build()
		).toList();
	}

	@Override
	public ArticleGetResp getOne(Long articleId) {
		var article = checkAndGetArticle(articleId);

		return ArticleGetResp.builder()
			.article(article)
			.build();
	}

	@Override
	public Long saveOne(ArticlePostReq req) {
		var user = checkAndGetUser(req.userId());
		var category = checkAndGetCategory(req.categoryId());

		return articleRepository.save(Article.builder()
				.user(user)
				.title(req.title())
				.content(req.content())
				.category(category)
				.build())
			.getId();
	}


	@Override
	public Long updateOne(ArticlePutReq req) {
		var article = checkAndGetArticle(req.articleId());

		article = article.toBuilder()
			.title(req.title())
			.content(req.content())
			.build();

		return articleRepository.save(article).getId();
	}

	@Override
	public Long deleteById(Long articleId) {
		var isArticleExists = articleRepository.existsById(articleId);

		if(!isArticleExists) {
			throw new IllegalArgumentException("해당 게시글 정보가 없습니다.");
		}

		articleRepository.deleteById(articleId);

		return articleId;
	}

	private User checkAndGetUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));
	}

	private Category checkAndGetCategory(Long id) {
		return categoryRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("카테고리 정보가 없습니다."));
	}

	private Article checkAndGetArticle(Long id) {
		return articleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글 정보가 없습니다."));
	}
}
