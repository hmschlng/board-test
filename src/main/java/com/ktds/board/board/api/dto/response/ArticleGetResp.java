package com.ktds.board.board.api.dto.response;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class ArticleGetResp {
	private Long articleId;
	private Long userId;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Long categoryId;
}
