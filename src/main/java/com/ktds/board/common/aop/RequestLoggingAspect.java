package com.ktds.board.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
public class RequestLoggingAspect {
    @Around("within(com.ktds.board.*.controller..*)")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            log.info("Method " + request.getMethod() + " [" + ZonedDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")) + "] "
                    + request.getRequestURI() + " from " + request.getRemoteAddr());
        }
    }

}

