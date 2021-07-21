package com.wootech.dropthecode.config.auth.service;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.service.AuthService;
import com.wootech.dropthecode.service.MemberService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("AuthService")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("access token이 유효한지 검증")
    void validatesAccessToken() {
        // given
        String invalidAccessToken = "invalid.access.token";
        given(jwtTokenProvider.validateToken(invalidAccessToken)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> authService.validatesAccessToken(invalidAccessToken))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    @DisplayName("유효한 토큰으로 Member 찾기")
    void findMemberByValidToken() {
        // given
        String validAccessToken = "valid.access.token";
        given(jwtTokenProvider.validateToken(validAccessToken)).willReturn(true);
        given(jwtTokenProvider.getPayload(validAccessToken)).willReturn("1");
        given(memberService.findById(1L))
                .willReturn(new Member(1L, "oauthId", "air", "air.junseo@gmail.com", "s3://image", Role.STUDENT, null));

        // when
        LoginMember loginMember = authService.findMemberByToken(validAccessToken);

        // then
        assertThat(loginMember.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("유효하지 토큰으로 Member 찾기")
    void findMemberByInvalidToken() {
        // given
        String invalidAccessToken = "invalid.access.token";
        given(jwtTokenProvider.validateToken(invalidAccessToken)).willReturn(false);

        // when
        LoginMember loginMember = authService.findMemberByToken(invalidAccessToken);

        // then
        assertThat(loginMember).isEqualTo(LoginMember.anonymous());
    }
}
