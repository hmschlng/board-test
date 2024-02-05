package com.ktds.board.board.api.dto.response;

import com.ktds.board.board.db.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentListGetResp {
    private Long id;
    private String content;
    private Long articleId;
    private Long userId;
    private Long parentId;
    private Integer step;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public CommentListGetResp(Comment comment) {
        Comment parent;
        this.id = comment.getId();
        this.content = (comment.getIsDeleted()) ? "삭제된 댓글입니다." : comment.getContent();
        this.articleId = comment.getArticle().getId();
        this.userId = comment.getUser().getId();
        this.parentId = Objects.isNull(parent = comment.getParent()) ? 0 : parent.getId();
        this.step = comment.getStep();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getLastModifiedAt();
    }
}
