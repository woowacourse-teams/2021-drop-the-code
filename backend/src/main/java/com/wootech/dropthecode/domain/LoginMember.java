package com.wootech.dropthecode.domain;

import com.wootech.dropthecode.exception.AuthenticationException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginMember {
    private static final LoginMember ANONYMOUS = new LoginMember();

    private Long id;

    public LoginMember(Long id) {
        this.id = id;
    }

    public static LoginMember anonymous() {
        return ANONYMOUS;
    }

    public void validatesAnonymous() {
        if (ANONYMOUS.equals(this)) {
            throw new AuthenticationException("유효하지 않은 유저입니다.");
        }
    }
}
