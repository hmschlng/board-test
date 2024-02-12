package com.ktds.board.auth.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OAuth2Attribute {
    /*
    OAuth2 인증 후 보내주는 데이터가 각 인증 서버바다 다르기 때문에 별도의 분기 처리가 필요
    */

    private Map<String, Object> attributes;
    private String provider;
    private String id;
    private String attributeKey;
    private String nickname;
    private String email;
    private String image;

    public static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes) {
        return switch (provider) {
            case "kakao" ->
                ofKakao(provider, attributeKey, attributes);
            case "google" ->
                ofGoogle(provider, attributeKey, attributes);
            case "naver" ->
                ofNaver(provider, attributeKey, attributes);
            default ->
                throw new RuntimeException("provider가 잘못되었습니다.");
        };
    }
//                1. attributeKey 가 "id" 인지 확인
//                System.out.println("attributeKey : " + attributeKey);

    private static OAuth2Attribute ofGoogle(String provider, String attributeKey, Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .provider(provider)
                .id(String.valueOf(attributes.get("sub")))
                .email((String) attributes.get("email"))
                .nickname((String) attributes.get("name"))
                .image(String.valueOf(attributes.get("picture")))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofKakao(String provider, String attributeKey, Map<String, Object> attributes) {
        var kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        var kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .provider(provider)
                .id(String.valueOf(attributes.get("id")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .nickname(String.valueOf(kakaoProfile.get("nickname")))
                .image(String.valueOf(kakaoProfile.get("profile_image_url")))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofNaver(String provider, String attributeKey, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .provider(provider)
                .id(String.valueOf(response.get("id")))
                .email((String) response.get("email"))
                .nickname((String) response.get("nickname"))
                .image(String.valueOf(response.get("profile_image")))
                .attributes(response)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        return Map.of(
            "provider", this.provider,
            "id", this.id,
            "key", this.attributeKey,
            "email", this.email,
            "nickname", this.nickname,
            "image", this.image
        );
    }
}
