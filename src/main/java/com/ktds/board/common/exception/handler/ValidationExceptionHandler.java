package com.ktds.board.common.exception.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @Validated 으로 유효성 검사를 진행한 @RequestParam 이나 @PathVariable 의 Validation Error 를 핸들링하는
     * 메소드입니다.
     * <p>
     * 컨트롤러 중 발생한 위반 사항들에 대해 사전에 정의된 메시지 양식으로 정제하고, 그 결과를 List<String> 형태로 body 에 담아 클라이언트에게 보여줍니다.
     *
     * @param ex 핸들링하려는 예외인 ConstraintViolationException
     * @return 제약조건 위반안내 메시지, 위반사항의 개수, 상세 메시지가 담긴 ProblemDetail
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {

        var violations = ex.getConstraintViolations().stream()
                .map((x) -> {
                            return new StringBuilder()
                                    .append("[")
                                    .append(x.getPropertyPath().toString().split("\\.")[1])
                                    .append("](은)는 ")
                                    .append(x.getMessage())
                                    .append(" 입력된 값: [")
                                    .append(x.getInvalidValue())
                                    .append("]")
                                    .toString();
                        }
                ).toList();

        var problemDetail = ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST, new StringBuilder()
                                .append("요청의 입력값이 올바르지 않습니다. (개수 ")
                                .append(violations.size())
                                .append("개)")
                                .toString());

        problemDetail.setTitle("Arguments Not Valid");
        problemDetail.setProperty("errors", violations);

        return problemDetail;
    }

    /**
     * &#064;Valid  로 유효성 검사를 진행한 DTO 에서 Validation Error 가 발생했을 때 클라이언트에게 위반 내용을 구체적으로 알릴 수 있도록
     * ResponseEntityExceptionHandler.handleMethodArgumentNotValid 메소드를 재정의했습니다.
     * <p>
     * 컨트롤러 중 발생한 위반 사항들에 대해 사전에 정의된 메시지 양식으로 정제하고, 그 결과를 List<String> 형태로 body 에 담아 클라이언트에게 보여줍니다.
     *
     * @param ex      핸들링하려는 예외인 MethodArgumentNotValidException
     * @param headers 응답에 사용될 헤더
     * @param status  상태 코드
     * @param request 현재 요청
     * @return 제약조건 위반안내 메시지, 위반사항의 개수, 상세 메시지가 담긴 ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {

        var violations = ex.getBindingResult().getFieldErrors().stream()
                .map((error) -> new StringBuilder()
                        .append("[")
                        .append(error.getField())
                        .append("](은)는 ")
                        .append(error.getDefaultMessage())
                        .append(" 입력된 값: [")
                        .append(error.getRejectedValue())
                        .append("]")
                        .toString())
                .toList();

        var problemDetail = ProblemDetail
                .forStatusAndDetail(ex.getStatusCode(), new StringBuilder()
                        .append("요청의 입력값이 올바르지 않습니다. (개수 ")
                        .append(violations.size())
                        .append("개)")
                        .toString());

        problemDetail.setTitle("Arguments Not Valid");
        problemDetail.setProperty("errors", violations);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    /**
     * 클라이언트가 보낸 JSON 형태의 요청 내용을 Parsing 할 수 없을 때 발생하는 오류에 대해 클라이언트에게 알리기 위해
     * ResponseEntityExceptionHandler.handleHttpMessageNotReadable 메소드를 재정의했습니다.
     *
     * @param ex      핸들링하려는 예외인 HttpMessageNotReadableException
     * @param headers 응답에 사용될 헤더
     * @param status  상태 코드
     * @param request 현재 요청
     * @return 제약조건 위반안내 메시지, 위반사항의 개수, 상세 메시지가 담긴 ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {

        var problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "클라이언트의 요청을 읽는데 실패했습니다.");

        problemDetail.setTitle("Request Cannot Parsed");
        problemDetail.setProperty("error", ex.getMessage().split(": ")[1]);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }
}

