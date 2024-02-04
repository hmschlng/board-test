package com.ktds.board.board.db.repository.comment;

import com.ktds.board.board.db.entity.Comment;
import com.ktds.board.board.db.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQuerydslRepository {

    @PersistenceUnit
    EntityManagerFactory emf;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findAllByArticleId(Long articleId) {

        var comments = queryFactory.selectFrom(QComment.comment)
                .leftJoin(QComment.comment).fetchJoin()
                .where(QComment.comment.article.id.eq(articleId))
                .orderBy(QComment.comment.parent.id.asc().nullsFirst(),
                        QComment.comment.createdAt.asc())
                .fetch();

        return comments;
    }
}
