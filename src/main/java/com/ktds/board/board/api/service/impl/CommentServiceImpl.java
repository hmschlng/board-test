package com.ktds.board.board.api.service.impl;

import com.ktds.board.board.api.dto.response.CommentListGetResp;
import com.ktds.board.board.api.dto.request.CommentPostReq;
import com.ktds.board.board.api.dto.request.CommentPutReq;
import com.ktds.board.board.api.service.CommentService;
import com.ktds.board.board.db.entity.Comment;
import com.ktds.board.board.db.repository.ArticleRepository;
import com.ktds.board.board.db.repository.comment.CommentRepository;
import com.ktds.board.user.db.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;
	private final UserInfoRepository userInfoRepository;

	@Override
	public List<CommentListGetResp> getAll(Long articleId) {
		var article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));

		var commentList = commentRepository.findAllByArticleId(articleId);

		return commentList.stream().map(comment ->
			CommentListGetResp.builder()
				.comment(comment)
				.build()
		).toList();
	}

	@Override
	public List<CommentListGetResp> getAllByUserId(Long userId) {
		var user = userInfoRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

		var commentList = commentRepository.findAllByUserId(userId);

		return commentList.stream().map(comment ->
			CommentListGetResp.builder()
				.comment(comment)
				.build()
		).toList();
	}

	@Override
	public Long saveOne(CommentPostReq req) {
		var user = userInfoRepository.findById(req.userId())
				.orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

		var article = articleRepository.findById(req.articleId())
				.orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없거나 삭제되었습니다."));

		Comment parentComment = null;
		var step = 0;

		if(Objects.nonNull(req.parentId())) {
			parentComment = checkAndGetComment(req.parentId());
			step = parentComment.getStep() + 1;
		}

		return commentRepository.save(Comment.builder()
			.content(req.content())
			.article(article)
			.user(user)
			.parent(parentComment)
			.step(step)
			.build()
		).getId();
	}

	@Override
	public Long updateOne(CommentPutReq req) {

		var comment = checkAndGetComment(req.id());

		if(comment.getIsDeleted()) {
			throw new IllegalArgumentException("이미 삭제된 댓글입니다.");
		}

		var updated = comment.toBuilder()
			.content(req.content())
			.build();

		return commentRepository.save(updated)
			.getId();
	}

	@Override
	public Long deleteById(Long id) {
		var comment = checkAndGetComment(id);

		commentRepository.deleteById(id);

		return comment.getId();
	}

	private Comment checkAndGetComment(Long id) {
		return commentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("댓글 정보가 없습니다."));
	}
}
