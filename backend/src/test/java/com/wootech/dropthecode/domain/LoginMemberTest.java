package com.wootech.dropthecode.domain;

import com.wootech.dropthecode.exception.AuthenticationException;
import com.wootech.dropthecode.exception.AuthorizationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
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
    @DisplayName("유효한 유저에 대해 검증하는 경우")
    void validMember() {
        // given
        LoginMember loginMember = new LoginMember(1L);

        // when
        // then
        assertThatNoException().isThrownBy(loginMember::validatesAnonymous);
    }

    @Test
    @DisplayName("로그인한 유저와 조회하려는 id가 같은 경우")
    void LoginMemberSameAsSearchId() {
        // given
        Long studentId = 1L;
        LoginMember loginMember = new LoginMember(1L);

        // when
        // then
        assertThatNoException().isThrownBy(() -> loginMember.validatesAuthorityToReview(studentId));
    }

    @Test
    @DisplayName("로그인한 유저와 조회하려는 id가 다른 경우 - 403에러 발생")
    void LoginMemberNotSameAsSearchId() {
        // given
        Long studentId = 1L;
        LoginMember loginMember = new LoginMember(2L);

        // when
        // then
        assertThatThrownBy(() -> loginMember.validatesAuthorityToReview(studentId))
                .isInstanceOf(AuthorizationException.class);
    }

}
