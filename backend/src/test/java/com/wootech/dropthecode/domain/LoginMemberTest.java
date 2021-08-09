package com.wootech.dropthecode.domain;

import com.wootech.dropthecode.exception.AuthenticationException;
import com.wootech.dropthecode.exception.AuthorizationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginMemberTest {

    @Test
    @DisplayName("토큰이 잘못된 경우 검증 - 401에러 발생")
    void wrongToken() {
        // given
        LoginMember loginMember = LoginMember.anonymous();

        // when
        // then
        assertThatThrownBy(loginMember::validatesAnonymous)
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    @DisplayName("로그인한 유저와 조회하려는 id가 다른 경우 - 403에러 발생")
    void LoginMemberTest() {
        // given
        Long studentId = 1L;
        LoginMember loginMember = new LoginMember(2L);

        // when
        // then
        assertThatThrownBy(() -> loginMember.validatesAuthorityToShowReview(studentId))
                .isInstanceOf(AuthorizationException.class);
    }

}
