package com.ktds.board.board.db.repository.comment;

import com.ktds.board.board.db.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQuerydslRepository {

}
