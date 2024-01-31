package com.ktds.board.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BaseResponseBody<T> {

    private Integer status;
    private String title;
    private T content;

}
