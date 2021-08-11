package com.wootech.dropthecode.service;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.controller.auth.util.RedisUtil;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.request.RefreshTokenRequest;
import com.wootech.dropthecode.dto.response.AccessTokenResponse;
import com.wootech.dropthecode.exception.AuthenticationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("AuthService")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberService memberService;

    @Mock
    private RedisUtil redisUtil;

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
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    @DisplayName("유효한 토큰으로 Member 찾기")
    void findMemberByValidToken() {
        // given
        String validAccessToken = "valid.access.token";
        given(jwtTokenProvider.validateToken(validAccessToken)).willReturn(true);
        given(jwtTokenProvider.getPayload(validAccessToken)).willReturn("1");
        given(memberService.findById(1L))
                .willReturn(dummyMember(1L, "oauthId", "air", "air.junseo@gmail.com", "s3://image", "github url", Role.STUDENT, null));

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

    @Test
    @DisplayName("만료된 access token을 만료되지 않은 refresh 토큰으로 갱신")
    void refreshAccessTokenCaseOne() {
        // given
        String refreshToken = "valid.refresh.token";
        String accessToken = "invalid.access.token";
        String newAccessToken = "new.access.token";
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(redisUtil.getData(anyString())).willReturn(refreshToken);
        given(jwtTokenProvider.createAccessToken(anyString())).willReturn(newAccessToken);

        // when
        AccessTokenResponse response = authService.refreshAccessToken(accessToken, new RefreshTokenRequest(refreshToken));

        // then
        assertThat(response.getAccessToken()).isEqualTo(newAccessToken);
    }

    @Test
    @DisplayName("만료된 access token을 만료된 refresh 토큰으로 갱신")
    void refreshAccessTokenCaseTwo() {
        // given
        String refreshToken = "invalid.refresh.token";
        String accessToken = "invalid.access.token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);

        // when
        // then
        assertThatThrownBy(() -> authService.refreshAccessToken(accessToken, refreshTokenRequest))
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    @DisplayName("만료되지 않은 access token을 만료된 refresh 토큰으로 갱신")
    void refreshAccessTokenCaseThree() {
        // given
        String refreshToken = "invalid.refresh.token";
        String accessToken = "valid.access.token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);

        // when
        // then
        assertThatThrownBy(() -> authService.refreshAccessToken(accessToken, refreshTokenRequest))
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    @DisplayName("만료되지 않은 access token을 만료되지 않은 refresh 토큰으로 갱신")
    void refreshAccessTokenCaseFour() {
        // given
        String refreshToken = "valid.refresh.token";
        String accessToken = "valid.access.token";
        String newAccessToken = "new.access.token";
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(redisUtil.getData(anyString())).willReturn(refreshToken);
        given(jwtTokenProvider.createAccessToken(anyString())).willReturn(newAccessToken);

        // when
        AccessTokenResponse response = authService.refreshAccessToken(accessToken, new RefreshTokenRequest(refreshToken));

        // then
        assertThat(response.getAccessToken()).isEqualTo(newAccessToken);
    }
}
