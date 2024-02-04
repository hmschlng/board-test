package com.ktds.board.board.db.repository.comment;

import com.ktds.board.board.db.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentQuerydslRepository {
    List<Comment> findAllByArticleId(Long articleId);
}
