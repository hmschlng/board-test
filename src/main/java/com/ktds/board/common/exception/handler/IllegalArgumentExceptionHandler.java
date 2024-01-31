package com.ktds.board.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {

    /**
     * IllegalArgumentException 예외를 내부 서버 에러 (500 Internal Server Error) 이 아닌
     * 클라이언트의 요청 에러 (400 Bad Request)로 표시하기 위해 ExceptionHandler 를 정의했습니다.
     *
     *
     * @param ex IllegalArgumentException
     * @return ProblemDetail (RFC 7807 양식의 ResponseBody)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {

        return ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage());
    }

}
