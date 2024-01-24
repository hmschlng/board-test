package com.ktds.board.db.entity;

import com.ktds.board.common.entity.ModifiedTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends ModifiedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
}
