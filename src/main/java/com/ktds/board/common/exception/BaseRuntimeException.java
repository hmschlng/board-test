package com.ktds.board.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
public class BaseRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;
    private RuntimeException exception;

    @Builder
    public BaseRuntimeException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.exception = new RuntimeException(message);
    }
}
