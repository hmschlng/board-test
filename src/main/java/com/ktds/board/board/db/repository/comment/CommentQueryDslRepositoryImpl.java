package com.ktds.board.board.db.repository.comment;

import com.ktds.board.board.db.entity.Comment;
import com.ktds.board.board.db.entity.QComment;
import com.ktds.board.user.db.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findAllByArticleId(Long articleId) {

        return queryFactory.selectFrom(QComment.comment)
                .leftJoin(QComment.comment).fetchJoin()
                .where(QComment.comment.article.id.eq(articleId))
                .orderBy(QComment.comment.parent.id.asc().nullsFirst(),
                        QComment.comment.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Comment> findAllByUserId(Long userId) {
        // FROM comment
        // INNER JOIN user
        // ON comment.userId == userId
        return queryFactory.selectFrom(QComment.comment)
            // .innerJoin(QUser.user).fetchJoin()
            .where(QComment.comment.user.id.eq(userId))
            .orderBy(QComment.comment.createdAt.desc())
            .fetch();
    }
}
