package com.wootech.dropthecode.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Auth 관련 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("OAuth 로그인 테스트")
    class OAuthLogin {

        @Test
        @DisplayName("성공")
        void oAuthLoginTestSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 provider 요청")
        void invalidProvider() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 code")
        void invalidCode() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 token")
        void invalidToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("access token 갱신")
    class RefreshAccessToken {

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 refresh token")
        void invalidRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료된 access token & 만료된 refresh token")
        void expiredAccessTokenAndExpiredRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료되지 않은 access token & 만료된 refresh token")
        void notExpiredAccessTokenAndExpiredRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료된 access token & 만료되지 않은 refresh token")
        void expiredAccessTokenAndNotExpiredRefreshToken() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("만료되지 않은 access token & 만료되지 않은 refresh token")
        void notExpiredAccessTokenAndNotExpiredRefreshToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("로그아웃")
    class Logout {

        @Test
        @DisplayName("로그 아웃 성공")
        void logOutSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }
    }
}
