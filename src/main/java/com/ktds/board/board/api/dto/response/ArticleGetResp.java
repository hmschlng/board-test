package com.ktds.board.board.api.dto.response;

import java.time.LocalDateTime;

import com.ktds.board.board.db.entity.Article;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ArticleGetResp {
	private Long articleId;
	private Long userId;
	private String userNickname;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Long categoryId;

	@Builder
	public ArticleGetResp(Article article) {
		this.articleId = article.getId();
		this.userId = article.getUserInfo().getId();
		this.userNickname = article.getUserInfo().getNickname();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.categoryId = article.getCategory().getId();
	}
}
