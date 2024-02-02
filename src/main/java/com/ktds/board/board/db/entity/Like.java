package com.ktds.board.board.db.entity;

import com.ktds.board.board.db.entity.enumtype.LikeContentType;
import com.ktds.board.common.entity.BaseTimeEntity;
import com.ktds.board.common.entity.ModifiedTimeEntity;
import com.ktds.board.user.db.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
@Entity
public class Like extends BaseTimeEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private LikeContentType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;
}
