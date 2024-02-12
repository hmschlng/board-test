package com.ktds.board.auth.api.service.impl;

import com.ktds.board.auth.api.dto.OAuth2Attribute;
import com.ktds.board.auth.db.entity.enumtype.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    /*
    OAuth2 인증을 완료하고 받은 데이터로 우리의 서비스에 접근할 수 있도록 인증 정보를 생성해주는 서비스
    */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        log.debug("accessToken = {}", userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        log.debug("oAuth2User = {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.debug("registrationId = {}", registrationId);
        log.debug("userNameAttributeName = {}", userNameAttributeName);
        log.debug("attributes = {}", oAuth2User.getAttributes());

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        var userAttribute = oAuth2Attribute.convertToMap();
        log.debug("userAttribute = {}", userAttribute);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
                userAttribute, "id");
    }
}
