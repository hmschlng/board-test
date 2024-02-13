package com.ktds.board.auth.api.util;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class MailDispatcher {

    private final JavaMailSenderImpl javaMailSender;

    public String sendAuthMail(String addr) throws MessagingException {
        String authCode = getAuthCode();

        var mimeMessage = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
        helper.setTo(addr);
        helper.setSubject("[KEY-BOARD] 메일 인증번호 확인");
        helper.setText("인증 코드를 홈페이지에 입력하세요\n"
                + "인증 코드 : "
                + authCode
        );

        javaMailSender.send(mimeMessage);

        return authCode;
    }

    private String getAuthCode() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        // 랜덤 숫자로 이루어진 6자리 인증코드 생성
        for(int i = 0; i<6; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    private String getTempPassword() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        // 랜덤 문자들로 이루어진 20자리 비밀번호 생성
        for(int i = 0; i<20; i++) {
            sb.append((char)(rand.nextInt(128 - 33) + 33));
        }
        return sb.toString();
    }

    public String sendPassChangeMail(String addr) throws MessagingException {
        var tempPw = getTempPassword();

        var mimeMessage = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom(javaMailSender.getUsername());
        helper.setTo(addr);
        helper.setSubject("[KEY-BOARD] 비밀번호 변경 메일입니다.");
        helper.setText("임시 비밀번호는 [ " + tempPw + " ] 입니다. 이 비밀번호로 로그인하셔서 비밀번호를 변경해 주세요.");

        javaMailSender.send(mimeMessage);

        return tempPw;
    }
}