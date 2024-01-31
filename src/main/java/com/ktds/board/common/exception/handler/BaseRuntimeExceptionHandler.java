package com.ktds.board.common.exception.handler;

import com.ktds.board.common.exception.BaseRuntimeException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseRuntimeExceptionHandler {

    /**
     * 서비스 로직에서 발생하는 Global 에러 형식에 대해 Http status 코드를 직접 정의하기 위해
     * RuntimeException 을 BaseRuntimeException 이라는 커스텀 예외 클래스로 정의했습니다.
     * <p>
     * 이 메소드는 BaseRuntimeException 을 처리하는 핸들러 입니다. RFC 7087 형식의 body 를 반환합니다.
     * <p>
     * 서비스 로직에 대한 예외 처리를 할 때 BaseRuntimeException 으로 throw 하면서
     * 상태 코드와 메시지를 정의해 주시면 됩니다.
     *
     * @param ex RuntimeException
     * @return ProblemDetail (RFC 7807 양식의 ResponseBody)
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public ProblemDetail handleBaseRuntime(BaseRuntimeException ex) {

        return ProblemDetail
                .forStatusAndDetail(
                        ex.getHttpStatus(),
                        ex.getException().getMessage());
    }

}
