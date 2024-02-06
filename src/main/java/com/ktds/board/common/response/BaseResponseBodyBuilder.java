//package com.ktds.board.common.entity;
//
//import lombok.NoArgsConstructor;
//import org.springframework.http.HttpStatus;
//
///**
// * ResponseBody 코드 간소화를 위한 Custom Builder 구현
// * SpringFramework에 미리 정의된 HttpStatus 를 활용해 ResponseBody 객체를 생성하기 위해
// * Builder Pattern 을 적용했습니다.
// */
//
//@NoArgsConstructor
//public class BaseResponseBodyBuilder<T> {
//    private HttpStatus status;
//    private T data;
//
//
//    public BaseResponseBodyBuilder<T> status(HttpStatus status) {
//        this.status = status;
//        return this;
//    }
//
//    public BaseResponseBodyBuilder<T> data(T data) {
//        this.data = data;
//        return this;
//    }
//
//    public BaseResponseBody<T> build() {
//        return new BaseResponseBody<>(status.value(), status.getReasonPhrase(), data);
//    }
//}
