package com.ktds.board.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 *
 */

@NoArgsConstructor
@Getter
public class BaseResponseBody<T> {
    private Integer status;
    private String title;
    private T data;

    public BaseResponseBody(HttpStatus httpStatus, T data) {
        this.status = httpStatus.value();
        this.title = httpStatus.getReasonPhrase();
        this.data = data;
    }
//    public static BaseResponseBodyBuilder builder() {
//        return new BaseResponseBodyBuilder();
//    }

}