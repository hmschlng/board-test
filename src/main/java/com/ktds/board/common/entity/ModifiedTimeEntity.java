package com.ktds.board.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class ModifiedTimeEntity extends BaseTimeEntity {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(updatable = false)
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
